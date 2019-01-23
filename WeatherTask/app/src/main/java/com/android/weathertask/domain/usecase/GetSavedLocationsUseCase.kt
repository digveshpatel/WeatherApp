package com.android.weathertask.domain.usecase

import com.android.weathertask.domain.UseCase
import com.android.weathertask.domain.model.LocationItem
import com.android.weathertask.domain.repository.LocationRepository
import javax.inject.Inject

class GetSavedLocationsUseCase @Inject constructor(private val locationRepository: LocationRepository) :
    UseCase<List<LocationItem>, Any>() {

    override fun buildUseCaseFlowable(params: Any) = locationRepository.getLocationData()
}
