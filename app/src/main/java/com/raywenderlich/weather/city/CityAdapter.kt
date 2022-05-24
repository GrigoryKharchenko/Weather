package com.raywenderlich.weather.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.weather.databinding.ItemCityBinding

class CityAdapter(
    private val choseCity: (String) -> Unit,
) :
//адаптер должен быть подклассом ViewHolder и добавляет поля
    RecyclerView.Adapter<CityViewHolder>() {
    //3 метода который всегоа переопредяеются в адаптаре
    //параметры будут всегда такие же, а возращаемый тип ViewHolder

    var cites: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {

        val itemView = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(itemView)
    }

    //связываются используеые текстовые метки с данными,
    // параметр position отвечает за позицию в списке по которой можно получить нужные данные
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cites[position], choseCity)
    }

    // возвращает количсетво элементов списка
    override fun getItemCount(): Int = cites.size
}
