package com.raywenderlich.weather.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("temp") val temp: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("wind") val wind: Double
)