package com.raywenderlich.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WeatherViewModel : BaseViewModel() {

    private val weatherLiveData = MutableLiveData<WeatherModel>()

    val liveData: LiveData<WeatherModel> = weatherLiveData

    init {
        loadWeatherData()
    }

    private fun loadWeatherData() {
        weatherLiveData.value = WeatherModel(
            "Omsk",
            14, "Rain",
            5,
            752,
            60,
            10
        )
    }
}
