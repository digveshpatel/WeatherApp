package com.android.weathertask.data.remote.intercepter

import android.content.Context
import com.android.weathertask.R
import com.android.weathertask.data.remote.exception.NetworkNotFoundException
import com.android.weathertask.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!NetworkUtils.hasNetwork(context)) {
            throw NetworkNotFoundException(context.resources.getString(R.string.txt_no_network))
        }
        return chain.proceed(request)
    }
}
