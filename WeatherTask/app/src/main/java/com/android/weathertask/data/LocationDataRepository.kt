package com.android.weathertask.data

import android.content.Context
import com.android.weathertask.data.local.db.PersistenceLocationEntityData
import com.android.weathertask.data.mapper.LocationMapper
import com.android.weathertask.domain.model.LocationItem
import com.android.weathertask.domain.repository.LocationRepository
import io.reactivex.Flowable
import javax.inject.Inject

class LocationDataRepository @Inject constructor(
    private val context: Context,
    private val locationMapper: LocationMapper
) : LocationRepository {
    override fun saveLocationData(
        locationId: String,
        locationName: String,
        locationLatitute: String,
        locationLongitude: String
    ): Flowable<Any> {
        return PersistenceLocationEntityData(context).saveLocation(
            locationId,
            locationName,
            locationLatitute,
            locationLongitude
        ).flatMapPublisher { Flowable.just(it) }
    }

    override fun getLocationData(): Flowable<List<LocationItem>> =
        PersistenceLocationEntityData(context).getLocationData().map { t -> locationMapper.transformLocationItems(t) }
}