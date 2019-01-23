package com.android.weathertask.domain.usecase

import com.android.weathertask.domain.UseCase
import com.android.weathertask.domain.repository.LocationRepository
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(private val locationRepository: LocationRepository) :
    UseCase<Any, SaveLocationUseCase.Param>() {

    override fun buildUseCaseFlowable(params: SaveLocationUseCase.Param) =
        locationRepository.saveLocationData(
            params.locationId,
            params.locationName,
            params.locationLatitute,
            params.locationLongitude
        )

    data class Param(
        val locationId: String,
        val locationName: String,
        val locationLatitute: String,
        val locationLongitude: String
    )
}

