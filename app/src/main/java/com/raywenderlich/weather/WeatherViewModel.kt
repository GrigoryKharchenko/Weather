package com.raywenderlich.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.weather.data.ApiInterface
import com.raywenderlich.weather.data.CoordResponse
import com.raywenderlich.weather.data.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : BaseViewModel() {

    val weatherLiveData = MutableLiveData<WeatherResponse>()

    init {
        getWeather()
    }

    fun getWeather() {
        val apiInterface = ApiInterface.create().getWeather()
        apiInterface.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>?,
                response: Response<WeatherResponse>?
            ) {
                if (response?.body() != null) {
                    Log.d("Movie", "Response body = ${response.body()}")
                    weatherLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                Log.d("Movie", "Error = ${t?.localizedMessage}")

            }

        })
    }
}
