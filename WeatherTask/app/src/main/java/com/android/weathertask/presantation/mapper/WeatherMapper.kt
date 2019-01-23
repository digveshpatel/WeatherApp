package com.android.weathertask.presantation.mapper

import com.android.weathertask.domain.model.WeatherData
import com.android.weathertask.presantation.model.WeatherModel
import javax.inject.Inject

class WeatherMapper @Inject constructor() {
    fun transformWeatherData(weatherData: WeatherData): WeatherModel {
        return WeatherModel(
            weatherData.iconUrl,
            weatherData.description,
            if (weatherData.temperature == null) null else "${Math.round((weatherData.temperature - 275.15))} °C",
            "${weatherData.country}  ${weatherData.name}",
            "${weatherData.humidity} humidity"
        )
    }
}