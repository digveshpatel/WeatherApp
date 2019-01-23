package com.android.weathertask.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.android.weathertask.presantation.base.OnFusedLocationChangeListener
import com.google.android.gms.location.*

class FusedLocation(activity: Activity) {

    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

    private val locationRequest = LocationRequest().apply {
        interval = Constant.LOCATION_UPDATE_INTERVAL
        fastestInterval = Constant.LOCATION_FAST_UPDATE_INTERVAL
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    private var onFusedLocationChangeListener: OnFusedLocationChangeListener? = null

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            onFusedLocationChangeListener?.onFusedLocationChange(locationResult?.lastLocation)
        }
    }

    private fun startLocationUpdates() {
        if (hasLocationPermission()) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
    }

    fun hasLocationPermission() = ActivityCompat.checkSelfPermission(
        fusedLocationClient.applicationContext,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
        fusedLocationClient.applicationContext,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    fun setOnFusedLocationChangeListener(onFusedLocationChangeListener: OnFusedLocationChangeListener) {
        this.onFusedLocationChangeListener = onFusedLocationChangeListener
        startLocationUpdates()
    }

    fun removeLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}