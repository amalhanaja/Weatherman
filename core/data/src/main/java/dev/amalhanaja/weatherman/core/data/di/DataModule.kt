package dev.amalhanaja.weatherman.core.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.amalhanaja.weatherman.core.data.repository.CityRepository
import dev.amalhanaja.weatherman.core.data.repository.CityRepositoryImpl
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataBinderModule {

        @Binds
        fun bindsCityRepository(cityRepositoryImpl: CityRepositoryImpl): CityRepository
    }

    @Provides
    @Singleton
    @DataDispatcher
    fun provideContextIo(): CoroutineContext {
        return Dispatchers.IO
    }
}
