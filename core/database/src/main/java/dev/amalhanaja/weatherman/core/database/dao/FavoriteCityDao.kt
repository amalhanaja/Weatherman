package dev.amalhanaja.weatherman.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.amalhanaja.weatherman.core.database.entity.FavoriteCity

@Dao
interface FavoriteCityDao {

    @Insert
    suspend fun insert(city: FavoriteCity)

    @Query("SELECT * FROM favorite_cites")
    suspend fun getFavoriteCities(): List<FavoriteCity>
}
