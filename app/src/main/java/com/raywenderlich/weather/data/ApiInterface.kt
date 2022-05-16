package com.raywenderlich.weather.data

import com.raywenderlich.weather.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


interface ApiInterface {
    @GET("data/2.5/weather?lat=35&lon=139&appid=b021ca9525ae87d6f27e414354290f9f")
    fun getWeather(): Call<WeatherResponse>

    companion object {

        private const val BASE_URL = "https://samples.openweathermap.org/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        private fun provideOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                client.addInterceptor(interceptor)
            }
            client.addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                response
            }
            return client.build()
        }
    }
}