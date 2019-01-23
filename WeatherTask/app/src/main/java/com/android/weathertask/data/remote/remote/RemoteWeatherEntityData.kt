package com.android.weathertask.data.remote.remote

import android.content.Context
import com.android.weathertask.data.WeatherEntityData
import com.android.weathertask.data.remote.exception.ExceptionParser
import com.android.weathertask.utils.Constant.WEATHER_APP_ID
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoteWeatherEntityData(context: Context) : CommonRetrofit<BaseApi>(context), WeatherEntityData {

    override fun getWeather(latitude: String, longitude: String) =
        getNetworkService()?.getWeatherInfo(latitude, longitude, WEATHER_APP_ID)
            ?.compose(applyServiceTransformer())!!

    override val restClass = BaseApi::class.java

    private fun <T> applyServiceTransformer(): FlowableTransformer<T, T> = FlowableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorResumeNext(ExceptionParser<Throwable, T>())

    }
}
