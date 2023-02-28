package dev.amalhanaja.weatherman.core.network.api

import com.google.gson.Gson
import dev.amalhanaja.weatherman.core.network.WeatherForecastNetworkDataSource
import dev.amalhanaja.weatherman.core.network.response.CityResponse
import dev.amalhanaja.weatherman.core.network.response.ForecastResponse
import dev.amalhanaja.weatherman.core.network.response.WeatherConditionResponse
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherForecastApiService {
    @GET("data/2.5/forecast?units=metric")
    suspend fun forecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
    ): ForecastResponse
}

@Singleton
class WeatherForecastApiClient @Inject constructor(
    gson: Gson,
    okHttpCallFactory: Call.Factory,
) : WeatherForecastNetworkDataSource {

    private val service: WeatherForecastApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrlProvider.provideOpenWeatherApiBaseUrl())
            .callFactory(okHttpCallFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create()
    }

    override suspend fun getForecastData(latitude: Double, longitude: Double): List<WeatherConditionResponse> {
        return service.forecast(latitude, longitude).list
    }
}
