package dev.amalhanaja.weatherman.core.network.di

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.amalhanaja.weatherman.core.network.BuildConfig
import dev.amalhanaja.weatherman.core.network.CityNetworkDataSource
import dev.amalhanaja.weatherman.core.network.api.CityApiClient
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface NetworkBinderModule {

        @Binds
        fun bindsCityApiClientToCityNetworkDataSource(cityApiClient: CityApiClient): CityNetworkDataSource
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideOkHttpCallFactory(): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                )
            )
            .addInterceptor { chain ->
                val newUrl = chain.request().url.newBuilder()
                    .addQueryParameter("appid", BuildConfig.OPEN_WEATHER_API_KEY)
                    .build()
                chain.proceed(request = chain.request().newBuilder().url(newUrl).build())
            }
            .build()
    }
}
