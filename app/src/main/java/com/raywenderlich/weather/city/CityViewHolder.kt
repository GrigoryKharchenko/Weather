package com.raywenderlich.weather.city

import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.weather.databinding.ItemCityBinding

//класс CityViewHolder с параметром биндинга от айтем сити хмл
class CityViewHolder(private val binding: ItemCityBinding) :
//описывает представление элемента и метаднные о его месте RecycleView
    RecyclerView.ViewHolder(binding.root) {
    //метод с 2 параметрами сити типа стринг и лямбда выражение
    fun bind(city: String, choseCity: (String) -> Unit) {
        binding.tvNameCity.text = city
        //binding.root обозначет что обращяемся ко всему представлению и делаю его весь кликабельный
        binding.root.setOnClickListener {
            choseCity.invoke(city)
        }
    }
}