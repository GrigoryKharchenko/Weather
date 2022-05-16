package com.raywenderlich.weather

data class WeatherModel(
    val city: String,
    val temperature: Double,
    val weather: String,
    val valueWind: Double,
    val valuePressure: Double,
    val valueHumidity: Double,
    val valueChanceRain: Int,
)
