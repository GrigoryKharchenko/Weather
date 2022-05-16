package com.raywenderlich.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.weather.data.ApiInterface
import com.raywenderlich.weather.data.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : BaseViewModel() {

    private val weatherLiveData = MutableLiveData<WeatherModel>()

    val liveData: LiveData<WeatherModel> = weatherLiveData

    init {
        loadWeatherData()
        getMovie()
    }

    private fun loadWeatherData() {
        weatherLiveData.value = WeatherModel(
            "Omsk",
            14.0,
            "Rain",
            5.0,
            752.0,
            60.0,
            10
        )
    }

    fun getMovie() {
        val apiInterface = ApiInterface.create().getWeather()
        apiInterface.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>?,
                response: Response<WeatherResponse>?
            ) {
                if (response?.body() != null) {
                    Log.d("Movie", "Response body = ${response.body()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                Log.d("Movie", "Error = ${t?.localizedMessage}")

            }
        })
    }
}
