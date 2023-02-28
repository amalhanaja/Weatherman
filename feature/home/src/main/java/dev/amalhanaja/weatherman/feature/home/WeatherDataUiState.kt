package dev.amalhanaja.weatherman.feature.home

import dev.amalhanaja.weatherman.core.model.Weather

sealed interface WeatherDataUiState {

    object Loading : WeatherDataUiState

    data class Error(val message: String) : WeatherDataUiState

    data class WithData(val data: List<Weather>) : WeatherDataUiState

}
