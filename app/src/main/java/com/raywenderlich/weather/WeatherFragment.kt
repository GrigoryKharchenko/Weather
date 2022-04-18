package com.raywenderlich.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.raywenderlich.weather.databinding.FragmentWeatherBinding

class WeatherFragment:Fragment() {
    private var binding:FragmentWeatherBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState:Bundle?): View?{
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding?.root
    }

    companion object{
        const val TAG = "WeatherFragment"

        fun newInstance() = WeatherFragment()
    }

}