package com.android.weathertask.utils

import io.reactivex.subscribers.ResourceSubscriber
//typealias onNext = (t: T)->Unit
//typealias onError = (t: Throwable?)->Unit
open class SimpleResourceSubscriber<T>(val onNext:(T)->Unit , val onError:(Throwable?)->Unit) : ResourceSubscriber<T>() {
    override fun onComplete() {

    }

    override fun onNext(t: T) {
        onNext.invoke(t)
    }

    override fun onError(t: Throwable?) {
        onError.invoke(t)
    }
}
