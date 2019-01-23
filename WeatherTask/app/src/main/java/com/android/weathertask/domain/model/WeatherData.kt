package com.android.weathertask.domain.model

data class WeatherData(
    val iconUrl: String?,
    val description: String?,
    val temperature: Double?,
    val country: String?,
    val name: String?,
    val humidity: Int?
)