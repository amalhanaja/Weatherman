package dev.amalhanaja.weatherman.core.network.api

import com.google.gson.Gson
import dev.amalhanaja.weatherman.core.network.CityNetworkDataSource
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

const val DEFAULT_LIMIT = 5

interface CityApiService {
    @GET("geo/1.0/direct")
    suspend fun searchCity(
        @Query("q") query: String,
        @Query("limit") limit: Int,
    ): List<CityResponse>

    @GET("data/2.5/forecast?units=metric")
    suspend fun forecast(
        @Query("lat") latitude: Double,
        @Query("longitude") longitude: Double,
    ): ForecastResponse
}

@Singleton
class CityApiClient @Inject constructor(
    gson: Gson,
    okHttpCallFactory: Call.Factory,
) : CityNetworkDataSource {

    private val service: CityApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrlProvider.provideOpenWeatherApiBaseUrl())
            .callFactory(okHttpCallFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create()
    }

    override suspend fun searchCity(query: String): List<CityResponse> {
        return service.searchCity(query, DEFAULT_LIMIT)
    }

    override suspend fun getForecastData(latitude: Double, longitude: Double): List<WeatherConditionResponse> {
        return service.forecast(latitude, longitude).list
    }
}
