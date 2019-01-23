package com.android.weathertask.data.local.db

import android.content.Context
import com.android.weathertask.data.LocationEntityData
import io.reactivex.Single

class PersistenceLocationEntityData(context: Context) : BasePersistence(context),
    LocationEntityData {
    override fun getDbName() = DATABASE_NAME

    override fun getLocationData() = db.locationDao.getLocations()


    override fun saveLocation(
        locationId: String,
        locationName: String,
        locationLatitute: String,
        locationLongitude: String
    ): Single<Long?> {
        val locationData = LocationData(locationId, locationName, locationLatitute, locationLongitude)
        return db.locationDao.insertLocation(locationData)
    }

    companion object {
        private const val DATABASE_NAME = "locations.db"
    }
}