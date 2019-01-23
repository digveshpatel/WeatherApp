package com.android.weathertask.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.weathertask.data.local.db.LocationData
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(locationData: LocationData): Single<Long?>

    @Query("Select * from LocationData")
    fun getLocations(): Flowable<List<LocationData>>

}