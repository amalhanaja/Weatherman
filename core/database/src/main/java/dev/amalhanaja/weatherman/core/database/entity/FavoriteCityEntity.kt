package dev.amalhanaja.weatherman.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "favorite_cities",
    primaryKeys = ["latitude", "longitude"]
)
data class FavoriteCity(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "state")
    val state: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
)
