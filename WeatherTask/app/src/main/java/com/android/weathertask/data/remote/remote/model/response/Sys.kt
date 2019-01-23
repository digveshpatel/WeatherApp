package com.android.weathertask.data.remote.remote.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Sys(

    @SerializedName("message")
    @Expose
    val message: Double?,
    @SerializedName("country")
    @Expose
    val country: String?,
    @SerializedName("sunrise")
    @Expose
    val sunrise: Int?,
    @SerializedName("sunset")
    @Expose
    val sunset: Int?
)