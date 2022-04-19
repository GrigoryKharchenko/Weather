package com.raywenderlich.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class WeatherViewModel : BaseViewModel() {

    private val users = MutableLiveData<List<String>>()

    val usersLiveData: LiveData<List<String>> = users

    init {
        loadUsers()
    }

     fun loadUsers() {
        users.value = listOf("Nikita", "Anton")
    }
}
