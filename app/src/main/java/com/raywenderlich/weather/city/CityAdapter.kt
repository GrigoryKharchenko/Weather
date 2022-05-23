package com.raywenderlich.weather.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.weather.databinding.ItemCityBinding

class CityAdapter(private val cites:List<String>):RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {

        val itemView = ItemCityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cites[position])
    }

    override fun getItemCount(): Int = cites.size

    class CityViewHolder(private val binding: ItemCityBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(city:String){
            binding.tvNameCity.text = city
        }
    }
}