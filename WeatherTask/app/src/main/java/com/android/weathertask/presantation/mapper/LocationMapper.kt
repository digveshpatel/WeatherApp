package com.android.weathertask.presantation.mapper

import com.android.weathertask.domain.model.LocationItem
import com.android.weathertask.presantation.model.LocationModel
import javax.inject.Inject

class LocationMapper @Inject constructor() {

    fun transformLocationItems(locationItems: List<LocationItem>?) = locationItems?.map { transformLocationItem(it) }

    fun transformLocationItem(locationItem: LocationItem) =
        LocationModel(locationItem.name, locationItem.lat, locationItem.long)
}