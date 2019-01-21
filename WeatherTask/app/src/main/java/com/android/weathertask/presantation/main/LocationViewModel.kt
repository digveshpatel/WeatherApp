package com.android.weathertask.presantation.main

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.weathertask.presantation.base.OnFusedLocationChangeListener
import com.android.weathertask.utils.Utility

class LocationViewModel : ViewModel(), OnFusedLocationChangeListener {

    var location = MutableLiveData<Location>()

    override fun onFusedLocationChange(location: Location) {
        Utility.log("CheckTasksThread: ==============>   " + location.latitude + "== " + location.longitude)
        this.location.postValue(location)
    }

}