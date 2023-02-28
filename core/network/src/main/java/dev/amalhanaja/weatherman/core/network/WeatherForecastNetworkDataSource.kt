package dev.amalhanaja.weatherman.core.network

import dev.amalhanaja.weatherman.core.network.response.WeatherConditionResponse

interface WeatherForecastNetworkDataSource {
    suspend fun getForecastData(latitude: Double, longitude: Double): List<WeatherConditionResponse>
}
