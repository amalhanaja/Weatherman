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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityRepository: CityRepository,
) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    val cityListUiState: StateFlow<CityListUiState> = snapshotFlow { searchQuery }
        .flatMapLatest { q ->
            val isGetFromFavorite = q.isBlank()
            val citiesFlow = if (isGetFromFavorite) getFavoriteCities() else searchCities(q)
            citiesFlow.map { cities ->
                when {
                    cities.isEmpty() && isGetFromFavorite -> CityListUiState.Empty
                    cities.isEmpty() -> CityListUiState.NotFound
                    else -> CityListUiState.WithData(isGetFromFavorite, cities)
                }
            }
        }.catch { emit(CityListUiState.Failed(it.message.orEmpty())) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CityListUiState.Empty,
        )

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }
    private fun getFavoriteCities(): Flow<List<City>> = cityRepository.getFavoriteCities()

    private fun searchCities(query: String): Flow<List<City>> = cityRepository.searchCities(query)

}
