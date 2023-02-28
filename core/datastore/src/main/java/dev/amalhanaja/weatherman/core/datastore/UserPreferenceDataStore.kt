package dev.amalhanaja.weatherman.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.amalhanaja.weatherman.core.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val KEY_CITY_NAME = stringPreferencesKey("city_name")
private val KEY_CITY_STATE = stringPreferencesKey("city_state")
private val KEY_CITY_COUNTRY = stringPreferencesKey("city_country")
private val KEY_CITY_LAT = doublePreferencesKey("city_lat")
private val KEY_CITY_LONG = doublePreferencesKey("city_long")

@Singleton
class UserPreferenceDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : UserPreferencesDataSource {

    override suspend fun setSelectedCity(city: City) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[KEY_CITY_NAME] = city.name
            mutablePreferences[KEY_CITY_STATE] = city.state
            mutablePreferences[KEY_CITY_COUNTRY] = city.country
            mutablePreferences[KEY_CITY_LAT] = city.latitude
            mutablePreferences[KEY_CITY_LONG] = city.longitude
        }
    }

    override fun getSelectedCity(): Flow<City?> = dataStore.data.map { preferences: Preferences ->
        val name = preferences[KEY_CITY_NAME] ?: return@map null
        val state = preferences[KEY_CITY_STATE] ?: return@map null
        val country = preferences[KEY_CITY_COUNTRY] ?: return@map null
        val latitude = preferences[KEY_CITY_LAT] ?: return@map null
        val longitude = preferences[KEY_CITY_LONG] ?: return@map null
        City(name, state, country, latitude, longitude)
    }
}
