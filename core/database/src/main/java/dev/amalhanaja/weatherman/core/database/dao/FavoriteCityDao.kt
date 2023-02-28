package dev.amalhanaja.weatherman.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.amalhanaja.weatherman.core.database.entity.FavoriteCity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCityDao {

    @Insert
    suspend fun insert(city: FavoriteCity)

    @Query("SELECT * FROM favorite_cities")
    fun getFavoriteCities(): Flow<List<FavoriteCity>>

    @Delete
    suspend fun delete(city: FavoriteCity)
}
