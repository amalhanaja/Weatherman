package dev.amalhanaja.weatherman.core.data.repository

import dev.amalhanaja.weatherman.core.data.di.DataDispatcher
import dev.amalhanaja.weatherman.core.data.mapper.toCity
import dev.amalhanaja.weatherman.core.data.mapper.toFavoriteCity
import dev.amalhanaja.weatherman.core.database.dao.FavoriteCityDao
import dev.amalhanaja.weatherman.core.datastore.UserPreferencesDataSource
import dev.amalhanaja.weatherman.core.model.City
import dev.amalhanaja.weatherman.core.network.CityNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

val DefaultCity = City(
    name = "Sidoarjo",
    state = "East Java",
    latitude = -7.4460408,
    longitude = 112.7178614,
    country = "ID"
)

class CityRepositoryImpl @Inject constructor(
    @DataDispatcher private val coroutineContext: CoroutineContext,
    private val cityNetworkDataSource: CityNetworkDataSource,
    private val userPreferencesDataSource: UserPreferencesDataSource,
    private val favoriteCityDao: FavoriteCityDao,
) : CityRepository {

    override fun getFavoriteCities(): Flow<List<City>> = favoriteCityDao.getFavoriteCities().map { cities -> cities.map { it.toCity() } }

    override suspend fun addToFavorite(city: City) {
        return favoriteCityDao.insert(city.toFavoriteCity())
    }

    override suspend fun removeFromFavorite(city: City) {
        favoriteCityDao.delete(city.toFavoriteCity())
    }

    override fun searchCities(query: String): Flow<List<City>> = flow {
        if (query.isNotBlank()) {
            val response = cityNetworkDataSource.searchCity(query)
            val cities = response.map { it.toCity() }
            emit(cities)
        } else {
            emit(emptyList())
        }
    }.flowOn(coroutineContext)

    override fun getCurrentCity(): Flow<City> {
        return userPreferencesDataSource.getSelectedCity().map { it ?: DefaultCity }
            .flowOn(coroutineContext)
    }

    override suspend fun setCurrentCity(city: City) {
        userPreferencesDataSource.setSelectedCity(city)
    }
}
