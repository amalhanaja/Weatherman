package dev.amalhanaja.weatherman.core.data.repository

import dev.amalhanaja.weatherman.core.data.di.DataDispatcher
import dev.amalhanaja.weatherman.core.model.Weather
import dev.amalhanaja.weatherman.core.network.WeatherForecastNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class WeatherForecastRepositoryImpl @Inject constructor(
    @DataDispatcher private val coroutineContext: CoroutineContext,
    private val weatherForecastNetworkDataSource: WeatherForecastNetworkDataSource,
) : WeatherForecastRepository {
    override fun getForecast(latitude: Double, longitude: Double): Flow<List<Weather>> = flow {
        val response = weatherForecastNetworkDataSource.getForecastData(latitude, longitude)
        val weathers = response.groupBy {
            Instant.fromEpochSeconds(it.dt).toLocalDateTime(TimeZone.currentSystemDefault()).date
        }.map { (date, list) ->
            val listCount = list.count()
            var tempMinMax = Float.MAX_VALUE to Float.MIN_VALUE
            var humidityTotal = 0
            var windDirectionDegreeTotal = 0
            var windSpeedTotal = 0f
            list.forEach { item ->
                tempMinMax = tempMinMax.copy(
                    first = listOf(tempMinMax.first, item.main.tempMin).min(),
                    second = listOf(tempMinMax.second, item.main.tempMax).max(),
                )
                humidityTotal += item.main.humidity
                windDirectionDegreeTotal += item.wind.deg
                windSpeedTotal += item.wind.speed
            }
            Weather(
                tempMinMax = tempMinMax,
                humidity = humidityTotal.div(listCount),
                date = date,
                windDirectionDegree = windDirectionDegreeTotal.div(listCount),
                windSpeed = windSpeedTotal.div(listCount),
                name = list.first().weather.first().main,
                iconId = list.first().weather.first().icon,
            )
        }.take(3)
        emit(weathers)
    }.flowOn(coroutineContext)
}
