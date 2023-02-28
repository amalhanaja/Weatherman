package dev.amalhanaja.weatherman.core.data.repository

import dev.amalhanaja.weatherman.core.data.di.DataDispatcher
import dev.amalhanaja.weatherman.core.data.mapper.toCity
import dev.amalhanaja.weatherman.core.model.City
import dev.amalhanaja.weatherman.core.network.CityNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CityRepositoryImpl @Inject constructor(
    @DataDispatcher private val coroutineContext: CoroutineContext,
    private val cityNetworkDataSource: CityNetworkDataSource,
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
        TODO("Not yet implemented")
    }

    override suspend fun setCurrentCity(city: City) {
        TODO("Not yet implemented")
    }
}
