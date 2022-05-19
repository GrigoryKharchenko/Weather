package com.raywenderlich.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.weather.data.ApiInterface
import com.raywenderlich.weather.data.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : BaseViewModel() {
    // изменяемый список который берет данные из WeatherResponse
    val weatherLiveData = MutableLiveData<WeatherResponse>()
    //метод с параметрами долготы и широты
     fun getWeather(lon:Float,lan:Float) {
        // реализация интерфейса которому передаются 2 параметра
        val apiInterface = ApiInterface.create().getWeather(lat = lan,lon = lon)
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
