package com.android.weathertask.data.remote.remote.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    @Expose
    val speed: Double?,
    @SerializedName("deg")
    @Expose
    val deg: Double?
)