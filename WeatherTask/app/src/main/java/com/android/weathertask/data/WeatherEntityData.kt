package com.android.weathertask.data

import com.android.weathertask.data.remote.remote.model.response.WeatherResponse
import io.reactivex.Flowable

interface WeatherEntityData {
    fun getWeather(latitude: String, longitude: String): Flowable<WeatherResponse>?
}
