package dev.amalhanaja.weatherman.core.network

import dev.amalhanaja.weatherman.core.network.response.CityResponse

interface CityNetworkDataSource {
    suspend fun searchCity(query: String): List<CityResponse>
}
