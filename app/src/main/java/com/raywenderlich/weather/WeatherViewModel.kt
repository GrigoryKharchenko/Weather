package com.raywenderlich.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.weather.data.ApiInterface
import com.raywenderlich.weather.data.WeatherResponse
import com.raywenderlich.weather.location.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : BaseViewModel() {
    // изменяемый список который берет данные из WeatherResponse
    val weatherLiveData = MutableLiveData<WeatherResponse>()
    private val _failLiveData = MutableLiveData<Network>()
    val failLiveData: LiveData<Network> = _failLiveData

    //метод с параметрами долготы и широты
    fun getWeather(lon: Float, lan: Float) {
        // реализация интерфейса которому передаются 2 параметра
        val apiInterface = ApiInterface.create().getWeather(lat = lan, lon = lon)
        apiInterface.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>?,
                response: Response<WeatherResponse>?
            ) {
                if (response?.body() != null) {
                    Log.d("Movie", "Response body = ${response.body()}")
                    weatherLiveData.value = response.body()
                }
                _failLiveData.value = Network.DEFOULT
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                _failLiveData.value = Network.ERROR
                Log.d("Movie", "Error = ${t?.localizedMessage}")
            }
        })
    }
}
