package dev.amalhanaja.weatherman.core.network

import dev.amalhanaja.weatherman.core.network.response.CityResponse
import dev.amalhanaja.weatherman.core.network.response.WeatherConditionResponse

interface CityNetworkDataSource {
    suspend fun searchCity(query: String): List<CityResponse>
    suspend fun getForecastData(latitude: Double, longitude: Double): List<WeatherConditionResponse>
}
