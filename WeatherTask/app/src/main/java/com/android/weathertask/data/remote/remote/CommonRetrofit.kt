package com.android.weathertask.data.remote.remote

import android.content.Context
import com.android.weathertask.BuildConfig
import com.android.weathertask.data.remote.intercepter.NetworkInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

abstract class CommonRetrofit<T>(private val context: Context) : BaseRetrofit<T>() {

    override val baseUrl = BuildConfig.WEATHER_URL

    override fun okHttpClientHandler(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.writeTimeout(60, TimeUnit.SECONDS)
        builder.addInterceptor(NetworkInterceptor(context))
        return super.okHttpClientHandler(builder)
    }
}