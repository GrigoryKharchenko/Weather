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
            "Omsk",
            14, "Rain",
            5,
            752,
            60,
            10
        )
    }
}
