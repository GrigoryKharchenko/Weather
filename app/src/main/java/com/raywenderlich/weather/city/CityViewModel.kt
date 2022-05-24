package com.raywenderlich.weather.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.weather.BaseViewModel

class CityViewModel : BaseViewModel() {
    //изменяемое хранилище данных в который будет помещен список из стрингов
    private val cites = MutableLiveData<List<String>>()

    //хранилище данных со списком стрингов
    val citesLiveData: LiveData<List<String>> = cites

    //метод котоырй задает значения для списка
    fun loadCites() {
        cites.value = listOf("Омск", "Москва", "Сочи", "Новосибирск", "Краснодар", "Тюмень")
    }
}
