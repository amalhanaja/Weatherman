package dev.amalhanaja.weatherman.core.network.api

import dev.amalhanaja.weatherman.core.network.BuildConfig

object BaseUrlProvider {
    fun provideOpenWeatherApiBaseUrl(): String {
        return BuildConfig.OPEN_WEATHER_BASE_URL
    }
}
