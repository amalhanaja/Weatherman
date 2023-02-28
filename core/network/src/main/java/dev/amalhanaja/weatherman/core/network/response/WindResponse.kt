package dev.amalhanaja.weatherman.core.network.response


import com.google.gson.annotations.SerializedName

data class WindResponse(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double,
    @SerializedName("speed")
    val speed: Double
)
