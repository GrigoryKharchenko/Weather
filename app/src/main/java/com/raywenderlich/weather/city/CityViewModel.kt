package com.raywenderlich.weather.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raywenderlich.weather.BaseViewModel

class CityViewModel : BaseViewModel() {
    //изменяемое хранилище данных в который будет помещен список из стрингов
    val citesLiveData = MutableLiveData<List<City>>()

    //метод котоырй задает значения для списка
    fun loadCites() {
        val citesRepository = CitesRepository()
        citesLiveData.value = citesRepository.getCity()
    }
}
