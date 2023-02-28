package dev.amalhanaja.weatherman.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.amalhanaja.weatherman.core.datastore.UserPreferenceDataStore
import dev.amalhanaja.weatherman.core.datastore.UserPreferencesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

private const val WEATHERMAN_PREFERENCE_NAME = "weatherman.preferences_pb"

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataStoreBinderModule {

        @Binds
        fun bindUserPreferenceDataSource(userPreferenceDataStore: UserPreferenceDataStore): UserPreferencesDataSource
    }

    @Provides
    @Singleton
    @DataStoreDispatcher
    fun provideDataStoreDispatcher(): CoroutineContext {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun provideDataStorePreferences(
        @ApplicationContext context: Context,
        @DataStoreDispatcher coroutineContext: CoroutineContext,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(coroutineContext + Job()),
            produceFile = { context.dataStoreFile(WEATHERMAN_PREFERENCE_NAME) }
        )
    }

}
