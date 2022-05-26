package com.raywenderlich.weather

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.weather.city.CityFragment
import com.raywenderlich.weather.data.ConvertTemperature
import com.raywenderlich.weather.data.WeatherResponse
import com.raywenderlich.weather.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    private var binding: FragmentWeatherBinding? = null
    private val viewModel: WeatherViewModel by viewModels()

    //клиент службы определения местооположения
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // экземпляр клиента службы определения местооположения
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        getCurrentLocation()
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding?.root
    }

    // получение текущего местоопооложения
    private fun getCurrentLocation() {
        //если выполняется проверка разрешений
        if (checkPermission()) {
            //если GPS включен на телефоне то выполняется
            requestLocation()
            //если разрешения нет
        } else {
            //то запрашиваются разрешения
            requestPermission()
        }
    }

    private fun requestLocation() {
        if (isLocationEnabled()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission()
                return
            }
            //запрос на последнее местоположение,затем возвращает значение в данном случае task
            // это значение можно использовать для получения локации объекта
            fusedLocationProviderClient?.lastLocation?.addOnCompleteListener(requireActivity()) { task ->
                val location: Location? = task.result
                //прооверяет если лоокация равна налу то выводится Null
                if (location == null)
                else {
                    //обращается к экземляру класса WeatherViewModel
                    //затем обращение к методу который передает долготу и широту
                    viewModel.getWeather(
                        location.longitude.toFloat(),
                        location.latitude.toFloat()
                    )
                }
            }
            //если GPS выключен
        } else {
            //вызывает настройки для активации локации
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    // проверяет включен ли GPS на телефоне
    private fun isLocationEnabled(): Boolean {
        // LocationManager этот класс предоставляет доступ к системным службам определения местоположения
        val locationManager: LocationManager =
            //as преоброзовывает типы в данном случае к типу LocationManager
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //вернуть
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    //запрашивание разрешений
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    //проверка разрешений
    private fun checkPermission(): Boolean {
        //если доступ к грубому расположению разрешен и доступ к местоположению дооступен то
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //возвращает тру
            return true
        }
        //если не выполняется фолс
        return false
    }

    //переопредеям метод с 3 параметрами
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //доступ к методу который находится в суперклассе Fragment
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //если
        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnClicks()
        //подписываюсь на обновление данных
        viewModel.failLiveData.observe(viewLifecycleOwner) { error ->
            Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.weatherLiveData.observe(viewLifecycleOwner) { weatherResponse ->
            setData(weatherResponse)
        }
    }

    private fun setData(value: WeatherResponse) {
        binding?.run {
            tvValueHumidity.text =
                getString(R.string.fragment_weather_value_humidity, value.main.humidity.toString())
            tvValueChanceRain.text =
                getString(R.string.fragment_weather_value_chance_rain, value.clouds.all.toString())
            tvValuePressure.text =
                getString(R.string.fragment_weather_value_pressure, value.main.pressure - 253)
            tvValueWind.text =
                getString(R.string.fragment_weather_value_wind, value.wind.speed.toString())
            tvCity.text = value.name
            tvValueTemperature.text =
                ConvertTemperature.convertInFahrenheit(viewModel.weatherLiveData.value?.main!!.temp)
                    .toString()
            tvWeather.text = value.weather[0].description
            pbCircle.visibility = View.GONE
            Glide.with(iBtnCloudy)
                .load("http://openweathermap.org/img/wn/${value.weather[0].icon}@2x.png")
                .into(iBtnCloudy)
        }
    }

    //объявляю метод showSnackBar с параметром типа стринг возвращам значение типа Unit
    private fun showSnackBar(value: String) {
        //(requireView()по стандарту во фрагментах,текст котооорой надо отобразить,время длительности сооообщения)
        Snackbar.make(requireView(), value, Snackbar.LENGTH_SHORT).show()
    }

    //обявление метода без параметров с возвращаемым значением Unit
    private fun initOnClicks() {
        binding?.run {
            //по нажатию радио батона
            rbCelsius.setOnClickListener {
                // текст вью = равняется значению перевода температуры
                tvValueTemperature.text = viewModel.weatherLiveData.value?.main?.let { weather ->
                    ConvertTemperature.convertInCelsius(
                        weather.temp
                    ).toString()
                }
            }
            rbFahrenheit.setOnClickListener {
                tvValueTemperature.text =
                    viewModel.weatherLiveData.value?.main?.let { weather ->
                        ConvertTemperature.convertInFahrenheit(weather.temp)
                            .toString()
                    }
            }
            tvMyLocation.setOnClickListener {
                viewModel.weatherLiveData.value?.let { name -> showSnackBar(name.name) }
            }
            //по нажатию переходит во фрагмент со списком городов
            tvChangeCity.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .add(R.id.container, CityFragment.newInstance(), TAG)
                    .addToBackStack("test")
                    .commit()
            }
        }
    }

    companion object {
        const val TAG = "WeatherFragment"
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 50// не имеет значения какое число
        fun newInstance() = WeatherFragment()
    }
}
