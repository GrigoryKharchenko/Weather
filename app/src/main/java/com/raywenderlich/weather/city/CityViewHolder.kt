package com.raywenderlich.weather.city

import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.weather.databinding.ItemCityBinding

//ViewHolder нужен для отображения
//класс CityViewHolder с параметром биндинга от айтем сити хмл
class CityViewHolder(private val binding: ItemCityBinding) :
//описывает представление элемента и метаднные о его месте RecycleView
    RecyclerView.ViewHolder(binding.root) {
    //метод с 2 параметрами сити типа стринг и лямбда выражение
    fun bind(city: City, onClick: (City) -> Unit) {
        binding.tvNameCity.text = city.name
    }
}
