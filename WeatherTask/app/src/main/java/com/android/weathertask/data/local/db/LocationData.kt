package com.android.weathertask.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationData(
    @PrimaryKey val locationId: String,
    val placeName: String,
    val latitude: String,
    val longitude: String
)