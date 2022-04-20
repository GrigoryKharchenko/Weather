package com.raywenderlich.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        //создаем экземпляр класса WeatherViewModel
        val viewModel: WeatherViewModel by viewModels()
        //я вызываю метод loadUsers() у экземпляра класса WeatherViewModel
//      viewModel.loadWeatherData()
        //вызываю функции initOnClicks без параметров
        initOnClicks()



        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            binding?.tvCity?.text = it.city
            binding?.tvValueTemperature?.text = it.temperature.toString()
            binding?.tvWeather?.text = it.weather
            binding?.tvValueWind?.text = it.valueWind.toString()
            binding?.tvValuePressure?.text = it.valuePressure.toString()
            binding?.tvValueHumidity?.text = it.valueHumidity.toString()
            binding?.tvValueChanceRain?.text = it.valueChanceRain.toString()
            setData(it)
        }
    }

    private fun setData(value:WeatherModel){
        binding?.tvValueHumidity?.text = getString(R.string.fragment_weather_value_humidity,value.valueHumidity.toString())
        binding?.tvValueChanceRain?.text = getString(R.string.fragment_weather_value_chance_rain,value.valueChanceRain.toString())
        binding?.tvValuePressure?.text = getString(R.string.fragment_weather_value_pressure,value.valuePressure.toString())
        binding?.tvValueWind?.text = getString(R.string.fragment_weather_value_wind,value.valueWind.toString())
    }

    //объявляю метод showSnackBar с параметром типа стринг возвращам значение типа Unit
    private fun showSnackBar(value: String) {
        //(requireView()по стандарту во фрагментах,текст котооорой еадо отобразить,время длительности сооообщения)
        Snackbar.make(requireView(), value, Snackbar.LENGTH_SHORT).show()
    }

    //обявление метода без параметров с возвращаемым значением Unit
    private fun initOnClicks() {
        binding?.rbCelsius?.setOnClickListener {
            // вызывается метоод showSnackBar с параметром типа стринг
            showSnackBar("C")
        }
        binding?.rbFahrenheit?.setOnClickListener {
            showSnackBar("F")
        }
        binding?.tvChangeCity?.setOnClickListener {
            openFragmentCity()
        }
        binding?.tvMyLocation?.setOnClickListener {
            showSnackBar("Мое местополоожение")
        }
    }

    private fun openFragmentCity() {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container, CityFragment.newInstance(), CityFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val TAG = "WeatherFragment"

        fun newInstance() = WeatherFragment()
    }

}
