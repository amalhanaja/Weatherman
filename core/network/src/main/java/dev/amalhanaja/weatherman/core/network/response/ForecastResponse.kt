package dev.amalhanaja.weatherman.core.network.response


import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("city")
    val city: CityDetailResponse,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<WeatherConditionResponse>,
    @SerializedName("message")
    val message: Int
)
