package dev.amalhanaja.weatherman.core.network.response


import com.google.gson.annotations.SerializedName

data class WeatherStatusResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)
