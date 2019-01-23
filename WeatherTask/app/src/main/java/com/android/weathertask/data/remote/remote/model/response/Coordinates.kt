package com.android.weathertask.data.remote.remote.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("lon")
    @Expose
    val lon: Double?,
    @SerializedName("lat")
    @Expose
    val lat: Double?
)
