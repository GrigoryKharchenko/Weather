package com.raywenderlich.weather.data

import com.raywenderlich.weather.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiInterface {
    //запрашиваю данные с ресурса
    @GET("data/2.5/weather?appid=${APPID}")
    fun getWeather(
        //сколько Query столько параметров отправляю
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("lang") lang: String = "ru"
        // запросы в классе Call с типом WeatherResponse
    ): Call<WeatherResponse>

    companion object {
        //значение известно во время компиляции
        private const val BASE_URL = "http://api.openweathermap.org/"
        private const val APPID = "b021ca9525ae87d6f27e414354290f9f"

        // метод c типом ApiInterface
        fun createApi(): ApiInterface {
            //создается неизменяемый объект
            val retrofit = Retrofit.Builder()
                //добавляется конвертер(какой конвертер.создать)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                //базовый юрл
                .baseUrl(BASE_URL)
                .build()
            // воозвращает готовый ретрофит
            return retrofit.create(ApiInterface::class.java)
        }

        //предоставление для okhttp
        private fun provideOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient().newBuilder()
                //время ожидания соединения период времени, в течение которого должно установиться соединение с целевым хостом
                .connectTimeout(15, TimeUnit.SECONDS)
                //Тайм-аут чтения применяется с момента успешного установления соединения между клиентом и целевым хостом.
                //Он определяет максимальное время бездействия между двумя пакетами данных при ожидании ответа сервера
                .readTimeout(60, TimeUnit.SECONDS)
                //Тайм-аут записи определяет максимальное время бездействия между двумя пакетами данных при отправке запроса на сервер.
                .writeTimeout(30, TimeUnit.SECONDS)
            //если функция, позволяющая запускать некоторый код только в режиме debug
            if (BuildConfig.DEBUG) {
                //добавление перехватчика
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
