package com.android.weathertask.data.mapper

import com.android.weathertask.data.local.db.LocationData
import com.android.weathertask.domain.model.LocationItem
import javax.inject.Inject

class LocationMapper @Inject constructor() {

    fun transformLocationItems(locationData: List<LocationData>?) = locationData?.map { transformLocationItem(it) }

    private fun transformLocationItem(locationData: LocationData) =
        LocationItem(locationData.locationId,locationData.placeName, locationData.latitude, locationData.longitude)
}