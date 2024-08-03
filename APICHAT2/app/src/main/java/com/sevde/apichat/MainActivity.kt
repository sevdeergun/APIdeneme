package com.example.weatherapp
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import com.example.weatherapp.RetrofitClient
import com.example.weatherapp.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etCityName: EditText
    private lateinit var btnGetWeather: Button
    private lateinit var tvWeatherInfo: TextView

    private val apiKey = "YOUR_API_KEY_HERE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCityName = findViewById(R.id.etCityName)
        btnGetWeather = findViewById(R.id.btnGetWeather)
        tvWeatherInfo = findViewById(R.id.tvWeatherInfo)

        btnGetWeather.setOnClickListener {
            val cityName = etCityName.text.toString()
            if (cityName.isNotEmpty()) {
                getWeatherData(cityName)
            }
        }
    }

    private fun getWeatherData(cityName: String) {
        val call = RetrofitClient.instance.getWeather(cityName, apiKey)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        val weatherInfo = "City: ${it.name}\n" +
                                "Temperature: ${it.main.temp}°C\n" +
                                "Description: ${it.weather[0].description}"
                        tvWeatherInfo.text = weatherInfo
                    }
                } else {
                    tvWeatherInfo.text = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                tvWeatherInfo.text = "Failed to retrieve data"
            }
        })
    }
}
