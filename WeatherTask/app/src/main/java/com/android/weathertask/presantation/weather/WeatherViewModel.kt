package com.android.weathertask.presantation.weather

import androidx.lifecycle.MutableLiveData
import com.android.weathertask.domain.model.WeatherData
import com.android.weathertask.domain.usecase.WeatherUseCase
import com.android.weathertask.presantation.base.BaseViewModel
import com.android.weathertask.presantation.mapper.WeatherMapper
import com.android.weathertask.presantation.model.WeatherModel
import com.android.weathertask.utils.SimpleResourceSubscriber
import javax.inject.Inject

class WeatherViewModel @Inject internal constructor(
    private val weatherUseCase: WeatherUseCase,
    private val weatherMapper: WeatherMapper
) : BaseViewModel() {

    val weatherLiveData = MutableLiveData<WeatherModel?>()

    val failedLiveData = MutableLiveData<String?>()

    fun getWeatherOfLocation(latitude: String, longitude: String) {
        weatherUseCase.executeFlowable(SimpleResourceSubscriber({ weatherData ->
            weatherLiveData.postValue(weatherMapper.transformWeatherData(weatherData))
        }, { error ->
            failedLiveData.postValue(error?.message)
        }), WeatherUseCase.Params(latitude, longitude))
    }

    override fun getUseCases() = listOf(weatherUseCase)
}