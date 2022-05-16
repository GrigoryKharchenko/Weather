package com.raywenderlich.weather

data class WeatherModel(
    val city: String? = null,
    val temperature: Int? = null,
    val weather: String? = null,
    val valueWind: Int? = null,
    val valuePressure: Int? = null,
    val valueHumidity: Int? = null,
    val valueChanceRain: Int? = null,
)
