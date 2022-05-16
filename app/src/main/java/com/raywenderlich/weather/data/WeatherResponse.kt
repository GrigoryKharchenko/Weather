package com.raywenderlich.weather.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") val coord:CoordResponse,
    @SerializedName("weather") val weather:List<Weather>,
    @SerializedName("base") val base:String,
    @SerializedName("main") val main:Main,
    @SerializedName("wind") val wind:Wind,
    @SerializedName("clouds") val clouds:Clouds,
    @SerializedName("dt") val dt:Int,
    @SerializedName("sys") val sys:Sys,
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String,
    @SerializedName("cod") val cod:Int,
)

data class CoordResponse(
    @SerializedName("lon") val lon: Float,
    @SerializedName("lat") val lat: Float
)

data class Weather(
    @SerializedName("id") val id:Int,
    @SerializedName("main") val main:String,
    @SerializedName("description") val description:String,
    @SerializedName("icon") val icon:String
)

data class Main(
    @SerializedName("temp") val temp:Double,
    @SerializedName("pressure") val pressure:Double,
    @SerializedName("humidity") val humidity:Int,
    @SerializedName("temp_min") val temp_min:Double,
    @SerializedName("temp_max") val temp_max:Double,
    @SerializedName("sea_level") val sea_level:Double,
    @SerializedName("grnd_level") val grnd_level:Double,
)

data class Wind(
    @SerializedName("speed") val speed:Double,
    @SerializedName("deg") val deg:Int
)

data class Clouds(
    @SerializedName("all") val all:Int
)

data class Sys(
    @SerializedName("message") val message:Double,
    @SerializedName("country") val country:String,
    @SerializedName("sunrise") val sunrise:Int,
    @SerializedName("sunset") val sunset:Int,
)