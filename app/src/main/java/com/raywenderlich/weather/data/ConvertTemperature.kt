package com.raywenderlich.weather.data

object ConvertTemperature {

    private const val KELVIN = 273.15

    fun convertInCelsius(temperature: Double): Double = temperature - KELVIN

    fun convertInFahrenheit(temperature: Double): Double = (temperature - KELVIN) * 9 / 5 + 32

    fun convertPressure(pressure: Double): Double = pressure - 253
}
