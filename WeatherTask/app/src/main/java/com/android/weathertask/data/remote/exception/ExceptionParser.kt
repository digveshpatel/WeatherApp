package com.android.weathertask.data.remote.exception

import io.reactivex.Flowable
import io.reactivex.functions.Function
import retrofit2.HttpException

class ExceptionParser<T : Throwable, X> : Function<T, Flowable<X>> {

    @Throws(Exception::class)
    override fun apply(t: T): Flowable<X> {
        if (t is HttpException) {
            val httpException = t as HttpException
            if (httpException.response() != null && httpException.response().errorBody() != null) {

            }
        }
        return Flowable.error(t)
    }
}
