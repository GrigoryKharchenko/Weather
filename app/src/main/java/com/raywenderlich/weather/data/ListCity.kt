package com.raywenderlich.weather.data

class ListCity() {
    fun getCity():List<City> =
        listOf(
            City(54.950443, 73.424466, "Омск"),
            City( 55.7558, 37.6173, "Москва"),
            City( 43.59917, 39.72569, "Сочи"),
            City( 55.0415, 82.9346, "Новосибирск"),
            City(  45.04484, 38.97603, "Краснодар"),
        )
}