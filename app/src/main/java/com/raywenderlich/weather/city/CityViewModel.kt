package com.raywenderlich.weather.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.weather.BaseViewModel

class CityViewModel : BaseViewModel() {

    private val cites = MutableLiveData<List<String>>()
    val citesLiveData: LiveData<List<String>> = cites

    fun loadCites() {
        cites.value = listOf("Омск", "Москва", "Сочи", "Новосибирск", "Краснодар","Тюмень")
    }
}