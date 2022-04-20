package com.raywenderlich.weather

data class WeatherModel(
    val city: String,
    val temperature:Int,
    val weather:String,
    val valueWind:Int,
    val valuePressure:Int,
    val valueHumidity:Int,
    val valueChanceRain:Int,
    )
