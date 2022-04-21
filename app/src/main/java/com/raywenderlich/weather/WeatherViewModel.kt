package com.raywenderlich.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class WeatherViewModel : BaseViewModel() {

    private val weatherData = MutableLiveData<WeatherModel>()

    val weatherLiveData: LiveData<WeatherModel> = weatherData

    init {
        loadWeatherData()
    }

    fun loadWeatherData() {
        weatherData.value = WeatherModel(
            city = "Omsk",
            temperature = 14,
            weather = "Rain",
            valueWind = 5,
            valuePressure = 752,
            valueHumidity = 70,
            valueChanceRain = 10
        )
    }
}
