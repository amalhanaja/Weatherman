package dev.amalhanaja.weatherman.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.amalhanaja.weatherman.core.database.dao.FavoriteCityDao
import dev.amalhanaja.weatherman.core.database.entity.FavoriteCity

@Database(
    entities = [
        FavoriteCity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class WeathermanDatabase : RoomDatabase() {
    abstract fun favoriteCityDao(): FavoriteCityDao
}
