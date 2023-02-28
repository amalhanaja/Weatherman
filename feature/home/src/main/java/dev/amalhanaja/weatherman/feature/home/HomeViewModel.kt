package dev.amalhanaja.weatherman.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.amalhanaja.weatherman.core.data.repository.CityRepository
import dev.amalhanaja.weatherman.core.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityRepository: CityRepository,
) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    private val _cityListUiState: MutableStateFlow<CityListUiState> = MutableStateFlow(CityListUiState.Empty)
    val cityListUiState: StateFlow<CityListUiState> = _cityListUiState
    fun retrySearch() {
        searchFlow(searchQuery)
            .onEach { _cityListUiState.value = it }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
        snapshotFlow { searchQuery }
            .flatMapLatest { q -> searchFlow(q) }
            .onEach { _cityListUiState.value = it }
            .launchIn(viewModelScope)
    }

    private fun searchFlow(query: String): Flow<CityListUiState> {
        val isGetFromFavorite = query.isBlank()
        val citiesFlow = if (isGetFromFavorite) getFavoriteCities() else searchCities(query)
        return citiesFlow.map { cities ->
            when {
                cities.isEmpty() && isGetFromFavorite -> CityListUiState.Empty
                cities.isEmpty() -> CityListUiState.NotFound
                else -> CityListUiState.WithData(isGetFromFavorite, cities)
            }
        }.catch { emit(CityListUiState.Failed(it.message.orEmpty())) }
    }

    private fun getFavoriteCities(): Flow<List<City>> = cityRepository.getFavoriteCities()

    private fun searchCities(query: String): Flow<List<City>> = cityRepository.searchCities(query)

}
