package com.android.weathertask.data.mapper

import com.android.weathertask.data.remote.remote.model.response.WeatherResponse
import com.android.weathertask.domain.model.WeatherData
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

    fun transformWeatherData(weatherResponse: WeatherResponse): WeatherData {
        val icon = weatherResponse.weather?.get(0)?.icon
        return WeatherData(
            icon?.let {
                "http://openweathermap.org/img/w/${weatherResponse.weather[0].icon}.png"
            } ?: run {
                null
            },
            weatherResponse.weather?.get(0)?.description,
            weatherResponse.main?.temp,
            weatherResponse.sys?.country,
            weatherResponse.name,
            weatherResponse.main?.humidity
        )
    }
}