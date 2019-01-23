package com.android.weathertask.data.remote.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRetrofit<T> {

    private var networkService: T? = null

    protected abstract val baseUrl: String

    protected abstract val restClass: Class<T>

    private fun initNetworkInterface() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = okHttpClientHandler(OkHttpClient.Builder())
            .addInterceptor(loggingInterceptor).build()
        val gson = gsonHandler(GsonBuilder().setPrettyPrinting())
            .setDateFormat(DATE_FORMAT).create()
        val retrofitBuilder = Retrofit.Builder().baseUrl(baseUrl)
        val retrofit = retrofitHandler(retrofitBuilder)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)).client(client).build()
        this.networkService = retrofit.create(restClass)
    }

    fun getNetworkService(): T? {
        if (this.networkService == null) {
            initNetworkInterface()
        }
        return this.networkService
    }

    open fun okHttpClientHandler(builder: OkHttpClient.Builder) = builder


    protected fun gsonHandler(builder: GsonBuilder) = builder

    companion object {

        val DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ssZ"
        protected fun retrofitHandler(builder: Retrofit.Builder): Retrofit.Builder {
            return builder
        }
    }
}
