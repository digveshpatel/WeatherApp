package com.android.weathertask.data

import android.content.Context
import com.android.weathertask.data.mapper.WeatherMapper
import com.android.weathertask.data.remote.remote.RemoteWeatherEntityData
import com.android.weathertask.domain.model.WeatherData
import com.android.weathertask.domain.repository.WeatherRepository
import io.reactivex.Flowable
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(
    private val context: Context,
    private val weatherMapper: WeatherMapper
) :
    WeatherRepository {

    override fun getWeatherFromLocation(latitude: String, longitude: String): Flowable<WeatherData> {
        return RemoteWeatherEntityData(context).getWeather(latitude, longitude)
            .map { weatherMapper.transformWeatherData(it) }
    }
}