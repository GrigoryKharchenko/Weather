package com.raywenderlich.weather


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.weather.databinding.ItemCityBinding

//объявляем класс с приватным параметроом котоорый возвращает список стрингов
//возвращает
class CityAdapter(private val cites: List<String>) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
// указывется индификатоор макета для отдельного элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(itemView)
    }
//переопределяет метод с параметрами
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cites[position])
    }
//возвращает количество элементов списка
    override fun getItemCount(): Int = cites.size

    class CityViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
//метод с параметром типа стринг который ничего не возвращает
        fun bind(city: String) {
            binding.tvCity.text = city
        }
    }
}
