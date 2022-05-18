package com.raywenderlich.weather

import android.annotation.SuppressLint
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
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        getCurrentLocation()
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding?.root

    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentLocation() {
        if (checkPermission()) {

            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission()
                    return
                }

                fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        Toast.makeText(requireContext(), "Null", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Get", Toast.LENGTH_SHORT).show()
//                        tvLat.text = "" + location.latitude
//                        tvLong.text = "" + location.longitude
                    }
                }

            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

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
                showSnackBar(R.string.fragment_weather_my_location.toString())
            }
        }
    }

    companion object {
        const val TAG = "WeatherFragment"
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100

        fun newInstance() = WeatherFragment()
    }
}
