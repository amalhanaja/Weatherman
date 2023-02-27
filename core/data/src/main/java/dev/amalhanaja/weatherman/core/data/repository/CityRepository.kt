package dev.amalhanaja.weatherman.core.data.repository

import dev.amalhanaja.weatherman.core.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    fun getFavoriteCities(): Flow<List<City>>
    suspend fun addToFavorite(city: City)
    suspend fun removeFromFavorite(city: City)
    suspend fun searchCities(query: String): Flow<List<City>>

    fun getCurrentCity(): Flow<City>
    suspend fun setCurrentCity(city: City)

}
