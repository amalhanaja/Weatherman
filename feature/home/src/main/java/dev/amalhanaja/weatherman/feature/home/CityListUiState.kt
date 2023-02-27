package dev.amalhanaja.weatherman.feature.home

import dev.amalhanaja.weatherman.core.model.City

sealed interface CityListUiState {
    object Empty : CityListUiState
    object Loading : CityListUiState
    data class Failed(val message: String) : CityListUiState
    data class WithData(val isFavoriteList: Boolean, val cities: List<City>) : CityListUiState
}
