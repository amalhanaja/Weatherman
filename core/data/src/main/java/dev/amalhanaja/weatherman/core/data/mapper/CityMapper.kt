package dev.amalhanaja.weatherman.core.data.mapper

import dev.amalhanaja.weatherman.core.database.entity.FavoriteCity
import dev.amalhanaja.weatherman.core.model.City
import dev.amalhanaja.weatherman.core.network.response.CityResponse

fun CityResponse.toCity(): City = City(
    name = name,
    state = state,
    country = country,
    latitude = latitude,
    longitude = longitude,
)

fun FavoriteCity.toCity(): City = City(
    name = name,
    state = state,
    country = country,
    latitude = latitude,
    longitude = longitude,
)

fun City.toFavoriteCity(): FavoriteCity = FavoriteCity(
    name = name,
    state = state,
    country = country,
    latitude = latitude,
    longitude = longitude,
)

