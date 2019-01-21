package com.android.weathertask.presantation.base

import android.location.Location

interface OnFusedLocationChangeListener {
    fun onFusedLocationChange(location: Location)
}