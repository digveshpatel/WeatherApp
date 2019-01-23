package com.android.weathertask.presantation.model

data class WeatherModel(
    val iconUrl: String?,
    val description: String?,
    val temperature: String?,
    val location: String?,
    val humidity: String?
)