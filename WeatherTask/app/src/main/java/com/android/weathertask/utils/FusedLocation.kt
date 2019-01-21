package com.android.weathertask.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.android.weathertask.presantation.base.OnFusedLocationChangeListener
import com.google.android.gms.location.*

class FusedLocation(activity: Activity) {

    val fusedLocationClient: FusedLocationProviderClient

    private val mLocationRequest: LocationRequest? = null

    private var onFusedLocationChangeListener: OnFusedLocationChangeListener? = null

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            if (onFusedLocationChangeListener != null)
                onFusedLocationChangeListener!!.onFusedLocationChange(locationResult!!.lastLocation)
        }
    }

    init {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        val mLocationRequest = LocationRequest()
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 30000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    private fun startLocationUpdates() {
        if (checkPermission()) {
            fusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper())
        }
    }

    fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            fusedLocationClient.applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            fusedLocationClient.applicationContext,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun setOnFusedLocationChangeListener(onFusedLocationChangeListener: OnFusedLocationChangeListener) {
        this.onFusedLocationChangeListener = onFusedLocationChangeListener
        startLocationUpdates()
    }

    fun removeLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}