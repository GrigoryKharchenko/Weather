package com.raywenderlich.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CItyViewModel : BaseViewModel() {

    private val cites = MutableLiveData<List<String>>()
    val citesLiveData: LiveData<List<String>> = cites

    fun loadCites() {
        cites.value = listOf("Oмск", "Москва", "Сантк-Петербург", "Ростоов-на-Дону", "Казань")

    }
}
