package dev.amalhanaja.weatherman.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.amalhanaja.weatherman.core.data.repository.CityRepository
import dev.amalhanaja.weatherman.core.data.repository.DefaultCity
import dev.amalhanaja.weatherman.core.data.repository.WeatherForecastRepository
import dev.amalhanaja.weatherman.core.model.City
import dev.amalhanaja.weatherman.core.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherForecastRepository: WeatherForecastRepository,
) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    init {
        getWeatherData()
        retrySearch()
    }

    private val _cityListUiState: MutableStateFlow<CityListUiState> = MutableStateFlow(CityListUiState.Empty)
    val cityListUiState: StateFlow<CityListUiState> = _cityListUiState
    val currentCityState: StateFlow<City> = cityRepository.getCurrentCity()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = DefaultCity
        )

    private val _weatherData = MutableStateFlow<WeatherDataUiState>(WeatherDataUiState.Loading)
    val weatherData: StateFlow<WeatherDataUiState> = _weatherData

    fun getWeatherData() {
        cityRepository.getCurrentCity().flatMapLatest {
            weatherForecastRepository.getForecast(it.latitude, it.longitude)
        }.map<List<Weather>, WeatherDataUiState> {
            WeatherDataUiState.WithData(it)
        }.catch {
            emit(WeatherDataUiState.Error(it.message.orEmpty()))
        }.onEach {
            _weatherData.emit(it)
        }.launchIn(viewModelScope)
    }

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

    fun onFavoriteCityChange(city: City, isFavorite: Boolean) {
        viewModelScope.launch {
            when {
                isFavorite -> cityRepository.addToFavorite(city)
                else -> cityRepository.removeFromFavorite(city)
            }
        }
    }

    fun selectCity(city: City) {
        viewModelScope.launch { cityRepository.setCurrentCity(city) }
    }

    private fun searchFlow(query: String): Flow<CityListUiState> {
        val isGetFromFavorite = query.isBlank()
        val citiesFlowWithFavorite = combine(getFavoriteCities(), searchCities(query)) { favorites, searchResults ->
            when {
                isGetFromFavorite -> favorites.map { city -> city to true }
                else -> searchResults.map { city ->
                    city to favorites.any { fav -> fav.latitude == city.latitude && fav.longitude == city.longitude }
                }
            }
        }
        return citiesFlowWithFavorite.map { cities ->
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
