package dev.amalhanaja.weatherman.core.network.response


import com.google.gson.annotations.SerializedName

data class WeatherConditionResponse(
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("main")
    val main: MainWeatherResponse,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<WeatherStatusResponse>,
    @SerializedName("wind")
    val wind: WindResponse
)
