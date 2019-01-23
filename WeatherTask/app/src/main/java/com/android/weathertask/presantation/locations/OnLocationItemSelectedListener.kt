package com.android.weathertask.presantation.locations

import com.android.weathertask.presantation.model.LocationModel

interface OnLocationItemSelectedListener {
    fun onLocationItemSelected(locationModal: LocationModel?)
}