package com.raywenderlich.weather.data

object ConvertTemperature {

    private const val TEMPERATURE_IN_KELVIN = 273.15

    fun convertInCelsius(temperature: Double): Double = temperature - TEMPERATURE_IN_KELVIN

    fun convertInFahrenheit(temperature: Double): Double =
        (temperature - TEMPERATURE_IN_KELVIN) * 9 / 5 + 32
}
