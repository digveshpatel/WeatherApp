package com.android.weathertask.data

import com.android.weathertask.data.local.db.LocationData
import io.reactivex.Flowable
import io.reactivex.Single

interface LocationEntityData {
    fun getLocationData(): Flowable<List<LocationData>>

    fun saveLocation(
        locationId: String,
        locationName: String,
        locationLatitute: String,
        locationLongitude: String
    ): Single<Long?>
}