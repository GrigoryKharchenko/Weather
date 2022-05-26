package com.raywenderlich.weather.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.weather.databinding.ItemCityBinding

//adapter занимается созданием и управлением ViewHolder
class CityAdapter(
    private val onClick: (City) -> Unit,
) :
//адаптер должен быть подклассом ViewHolder и добавляет поля
    RecyclerView.Adapter<CityViewHolder>() {
    //3 метода который всегоа переопредяеются в адаптаре
    //параметры будут всегда такие же, а возращаемый тип ViewHolder
    var cites: List<City> = listOf()

    //метод вызывается для создания объекта ViewHolder,
// в конструктор которого необходимо передать созданный View-компонент
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(itemView)
    }

    //связываются используемые текстовые метки с данными,
    // параметр position отвечает за позицию в списке по которой можно получить нужные данные
    //управлнеи viewHolder
    //вызывается столько раз сколько элементов
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = cites[position]
        holder.bind(item, onClick)
    }

    // возвращает количсетво элементов списка
    override fun getItemCount(): Int = cites.size
}
