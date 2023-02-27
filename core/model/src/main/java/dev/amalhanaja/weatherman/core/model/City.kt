package dev.amalhanaja.weatherman.core.model

data class City(
    val name: String,
    val localName: String?,
    val country: String,
    val latitude: Double,
    val longitude: Double,
)
