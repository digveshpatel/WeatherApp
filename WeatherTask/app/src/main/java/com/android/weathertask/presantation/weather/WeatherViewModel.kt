package com.android.weathertask.presantation.weather

import androidx.lifecycle.MutableLiveData
import com.android.weathertask.domain.model.WeatherData
import com.android.weathertask.domain.usecase.WeatherUseCase
import com.android.weathertask.presantation.base.BaseViewModel
import com.android.weathertask.presantation.mapper.WeatherMapper
import com.android.weathertask.presantation.model.WeatherModel
import io.reactivex.subscribers.ResourceSubscriber
import javax.inject.Inject

class WeatherViewModel @Inject internal constructor(
    private val weatherUseCase: WeatherUseCase,
    private val weatherMapper: WeatherMapper
) : BaseViewModel() {

    val weatherLiveData = MutableLiveData<WeatherModel?>()

    val failedLiveData = MutableLiveData<String?>()

    fun getWeatherOfLocation(latitude: String, longitude: String) {
        weatherUseCase.executeFlowable(object : ResourceSubscriber<WeatherData>() {
            override fun onNext(weatherData: WeatherData) {
                weatherLiveData.postValue(weatherMapper.transformWeatherData(weatherData))
            }

            override fun onError(t: Throwable) {
                failedLiveData.postValue(t.message)
            }

            override fun onComplete() {
                //no-op
            }
        }, WeatherUseCase.Params(latitude, longitude))
    }

    override fun clearSubscriptions() {
        weatherUseCase.clearAllSubscription()
    }
}