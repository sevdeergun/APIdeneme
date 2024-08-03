package com.example.weatherapp

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val name: String
)

data class Weather(
    val description: String
)

data class Main(
    val temp: Float
)


