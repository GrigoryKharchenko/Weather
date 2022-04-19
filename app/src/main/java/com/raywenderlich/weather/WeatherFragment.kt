package com.raywenderlich.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.weather.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    private var binding: FragmentWeatherBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeCity()
        clickDegreesListener()
        clickLocation()
    }

    private fun showSnackBar(value: String) {
        Snackbar.make(requireView(), value, Snackbar.LENGTH_SHORT).show()
    }

    private fun changeCity() {
        binding?.tvChangeCity?.setOnClickListener {
            showSnackBar("Сменить город")
        }
    }

    private fun clickDegreesListener() {
        binding?.rbCelsius?.setOnClickListener {
            showSnackBar("C")
        }
        binding?.rbFahrenheit?.setOnClickListener {
            showSnackBar("F")
        }
    }

    private fun clickLocation() {
        binding?.tvMyLocation?.setOnClickListener {
            showSnackBar("Мое местополоожение")
        }
    }

    companion object {
        const val TAG = "WeatherFragment"

        fun newInstance() = WeatherFragment()
    }
}
