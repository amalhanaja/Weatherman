package dev.amalhanaja.weatherman.core.datastore

import dev.amalhanaja.weatherman.core.model.City
import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataSource {
    suspend fun setSelectedCity(city: City)
    fun getSelectedCity(): Flow<City?>
}
