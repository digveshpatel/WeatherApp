package com.android.weathertask.domain.usecase

import com.android.weathertask.domain.UseCase
import com.android.weathertask.domain.model.WeatherData
import com.android.weathertask.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) :
    UseCase<WeatherData, WeatherUseCase.Params>() {

    override fun buildUseCaseFlowable(params: Params) =
        weatherRepository.getWeatherFromLocation(params.latitude, params.longitude)

    data class Params(val latitude: String, val longitude: String)
}
