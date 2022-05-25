package com.raywenderlich.weather.data

// TODO зачем object?
object ConvertTemperature {

    private const val KELVIN = 273.15 // TODO полменять название

    const val APPID = "b021ca9525ae87d6f27e414354290f9f"

    fun convertInCelsius(temperature: Double): Double = temperature - KELVIN

    fun convertInFahrenheit(temperature: Double): Double = (temperature - KELVIN) * 9 / 5 + 32
}
