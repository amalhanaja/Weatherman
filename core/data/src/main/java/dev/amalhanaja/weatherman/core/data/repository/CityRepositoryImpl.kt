package dev.amalhanaja.weatherman.core.data.repository

import dev.amalhanaja.weatherman.core.data.di.DataDispatcher
import dev.amalhanaja.weatherman.core.data.mapper.toCity
import dev.amalhanaja.weatherman.core.datastore.UserPreferencesDataSource
import dev.amalhanaja.weatherman.core.model.City
import dev.amalhanaja.weatherman.core.network.CityNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CityRepositoryImpl @Inject constructor(
    @DataDispatcher private val coroutineContext: CoroutineContext,
    private val cityNetworkDataSource: CityNetworkDataSource,
    private val userPreferencesDataSource: UserPreferencesDataSource,
) : CityRepository {
    override fun getFavoriteCities(): Flow<List<City>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorite(city: City) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorite(city: City) {
        TODO("Not yet implemented")
    }

    override fun searchCities(query: String): Flow<List<City>> = flow {
        val response = cityNetworkDataSource.searchCity(query)
        val cities = response.map { it.toCity() }
        emit(cities)
    }.flowOn(coroutineContext)

    override fun getCurrentCity(): Flow<City> {
        val defaultCity = City(
            name = "Sidoarjo",
            state = "East Java",
            latitude = -7.4460408,
            longitude = 112.7178614,
            country = "ID"
        )
        return userPreferencesDataSource.getSelectedCity().map { it ?: defaultCity }
            .flowOn(coroutineContext)
    }

    override suspend fun setCurrentCity(city: City) {
        userPreferencesDataSource.setSelectedCity(city)
    }
}
