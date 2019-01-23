package com.android.weathertask.domain.repository

import com.android.weathertask.domain.model.LocationItem
import io.reactivex.Flowable

interface LocationRepository {
    fun saveLocationData(
        locationId: String,
        locationName: String,
        locationLatitute: String,
        locationLongitude: String
    ): Flowable<Any>

    fun getLocationData(): Flowable<List<LocationItem>>
}