package com.raywenderlich.weather.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    fun getWeather(
        @Query("lang") lang:String = "ru"
    ):WeatherResponse

}