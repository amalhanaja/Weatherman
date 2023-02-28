package dev.amalhanaja.weatherman.core.model

import kotlinx.datetime.LocalDate

data class Weather(
    val tempMinMax: Pair<Float, Float>,
    val humidity: Int,
    val windDirectionDegree: Int,
    val windSpeed: Float,
    val date: LocalDate,
)
