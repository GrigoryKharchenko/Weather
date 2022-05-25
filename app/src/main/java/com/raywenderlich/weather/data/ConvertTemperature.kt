package com.raywenderlich.weather.data

// TODO зачем object?
object ConvertTemperature {

    private const val KELVIN = 273.15 // TODO полменять название

    fun convertInCelsius(temperature: Double): Double = temperature - KELVIN

    fun convertInFahrenheit(temperature: Double): Double = (temperature - KELVIN) * 9 / 5 + 32
}
