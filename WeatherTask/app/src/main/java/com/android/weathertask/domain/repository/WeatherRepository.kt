package com.android.weathertask.domain.repository

import com.android.weathertask.domain.model.WeatherData
import io.reactivex.Flowable

interface WeatherRepository {
    fun getWeatherFromLocation(latitude: String, longitude: String): Flowable<WeatherData>
}