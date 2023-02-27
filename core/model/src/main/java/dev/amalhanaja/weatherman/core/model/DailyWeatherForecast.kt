package dev.amalhanaja.weatherman.core.model

class DailyWeatherForecast(
    val tempMinMax: Pair<Float, Float>,
    val humidity: Int,
    val windDirectionDegree: Float,
    val windSpeed: Float,
    val dateTimeInMillis: Long,
)
