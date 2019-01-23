package com.android.weathertask.data.remote.remote

import com.android.weathertask.data.remote.remote.model.response.WeatherResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface BaseApi {

    @GET("/data/2.5/weather?")
    fun getWeatherInfo(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("APPID") appId: String): Flowable<WeatherResponse>

}