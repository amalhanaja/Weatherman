package dev.amalhanaja.weatherman.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.amalhanaja.weatherman.core.database.WeathermanDatabase
import dev.amalhanaja.weatherman.core.database.dao.FavoriteCityDao
import javax.inject.Singleton

private const val WEATHERMAN_DB_NAME = "weatherman-database"


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideWeathermanDatabase(
        @ApplicationContext context: Context,
    ): WeathermanDatabase {
        return Room.databaseBuilder(
            context,
            WeathermanDatabase::class.java,
            WEATHERMAN_DB_NAME,
        ).build()
    }

    @Provides
    fun provideFavoriteCityDao(weathermanDatabase: WeathermanDatabase): FavoriteCityDao {
        return weathermanDatabase.favoriteCityDao()
    }

}
