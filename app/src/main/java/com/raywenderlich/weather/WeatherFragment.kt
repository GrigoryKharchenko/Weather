package com.raywenderlich.weather

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.weather.data.WeatherResponse
import com.raywenderlich.weather.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {
    private var binding: FragmentWeatherBinding? = null
    private val viewModel: WeatherViewModel by viewModels()

    //клиент службы определения местооположения
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

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
            if (isLocationEnabled()) {

                //запрос на последнее местоположение,затем возвращает значение в данном случае task
                // это значение можно использовать для получения локации объекта
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    //прооверяет если лоокация равна налу то выводится Null
                    if (location == null) {
                        Toast.makeText(requireContext(), "Null", Toast.LENGTH_SHORT).show()
                        //иначе вывоодится Get
                    } else {
                        Toast.makeText(requireContext(), "Get", Toast.LENGTH_SHORT).show()

                        Log.d("Long", "location = ${location.latitude}")
                        Log.d("Long", "long = ${location.longitude}")
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
                //отоброжается Включите GPS
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_SHORT).show()
                //вызывает настройки для активации локации
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            //если разрешения нет
        } else {
            //то запрашиваются разрешения
            requestPermission()
        }
    }

    // проверяет включен ли GPS на телефоне
    private fun isLocationEnabled(): Boolean {
        // LocationManager этот класс предоставляет доступ к системным службам определения местоположения
        val locationManager: LocationManager =
            //as преобброзовывает типы в данном случае к типу LocationManager
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
            ), PERMISSION_REQUEST_ACCESS_LOCATION
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
                Toast.makeText(requireContext(), "Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnClicks()
        //подписываюсь на обновление данных 
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            Log.d("Message", "fdcgxrdgrdgd")
            setData(it)
        }
    }

    private fun setData(value: WeatherResponse) {
        binding?.run {
            tvValueHumidity.text =
                getString(R.string.fragment_weather_value_humidity, value.main.humidity.toString())
            tvValueChanceRain.text =
                getString(R.string.fragment_weather_value_chance_rain, value.clouds.all.toString())
            tvValuePressure.text =
                getString(R.string.fragment_weather_value_pressure, value.main.pressure)
            tvValueWind.text =
                getString(R.string.fragment_weather_value_wind, value.wind.speed.toString())
            tvCity.text = value.name
            tvValueTemperature.text = value.main.temp.toString()
            tvWeather.text = value.weather[0].description
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
                showSnackBar(getString(R.string.fragment_weather_my_location))
            }
        }
    }

    companion object {
        const val TAG = "WeatherFragment"
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 50//??

        fun newInstance() = WeatherFragment()
    }
}
