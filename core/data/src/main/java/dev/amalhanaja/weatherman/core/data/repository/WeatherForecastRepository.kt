package dev.amalhanaja.weatherman.core.data.repository

import dev.amalhanaja.weatherman.core.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherForecastRepository {
    fun getForecast(latitude: Double, longitude: Double): Flow<List<Weather>>
}
