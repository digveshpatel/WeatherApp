package com.android.weathertask.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.weathertask.data.local.db.dao.LocationDao

@Database(entities = [LocationData::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
}
