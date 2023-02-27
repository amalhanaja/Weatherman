package dev.amalhanaja.weatherman.core.network.response

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("name")
    val name: String = "",

    @SerializedName("local_names")
    val localNames: Map<String, String> = emptyMap(),

    @SerializedName("lat")
    val latitude: Double = 0.0,

    @SerializedName("lon")
    val longitude: Double = 0.0,

    @SerializedName("country")
    val country: String = "",

    @SerializedName("state")
    val state: String? = null,

)
