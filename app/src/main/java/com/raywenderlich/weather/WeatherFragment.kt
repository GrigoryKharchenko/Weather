package com.raywenderlich.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.weather.data.WeatherResponse
import com.raywenderlich.weather.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    private var binding: FragmentWeatherBinding? = null
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnClicks()

        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            Log.d("Message", "fdcgxrdgrdgd")
            setData(it)
        }
    }

    private fun setData(value: WeatherResponse) {
        binding?.run {
            tvValueHumidity.text = value.main.humidity.toString()
            tvValueChanceRain.text = value.clouds.all.toString()
            tvValuePressure.text = value.main.pressure.toString()
            tvValueWind.text = value.wind.speed.toString()
            tvCity.text = value.name
            tvValueTemperature.text = value.main.temp.toString()
            tvWeather.text = value.weather[0].main
            Glide.with(iBtnCloudy)
                .load("http://openweathermap.org/img/wn/${value.weather[0].icon}@2x.png")
                .into(iBtnCloudy)
        }
    }

    //объявляю метод showSnackBar с параметром типа стринг возвращам значение типа Unit
    private fun showSnackBar(value: String) {
        //(requireView()по стандарту во фрагментах,текст котооорой еадо отобразить,время длительности сооообщения)
        Snackbar.make(requireView(), value, Snackbar.LENGTH_SHORT).show()
    }

    //обявление метода без параметров с возвращаемым значением Unit
    private fun initOnClicks() {
        binding?.let {
            it.rbCelsius.setOnClickListener {
                // вызывается метоод showSnackBar с параметром типа стринг
                showSnackBar("C")
            }
            it.rbFahrenheit.setOnClickListener {
                showSnackBar("F")
            }
            it.tvMyLocation.setOnClickListener {
                showSnackBar(R.string.fragment_weather_my_location.toString())
            }
        }
    }

    companion object {
        const val TAG = "WeatherFragment"

        fun newInstance() = WeatherFragment()
    }
}
