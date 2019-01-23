package com.android.weathertask.domain

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

abstract class UseCase<T, Params> protected constructor() {

    private var compositeDisposable: CompositeDisposable? = null

    init {
        initCompositeSubscription()
    }

    private fun initCompositeSubscription() {
        compositeDisposable = CompositeDisposable()
    }

    fun clearAllSubscription() {
        compositeDisposable?.clear()

    }

    fun executeFlowable(subscriber: ResourceSubscriber<T>, params: Params = Any() as Params) = addSubscription(
        executeAsFlowable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(subscriber)
    )

    fun executeAsFlowable(params: Params = Any() as Params) = buildUseCaseFlowable(params)


    protected abstract fun buildUseCaseFlowable(params: Params): Flowable<T>

    protected fun addSubscription(subscription: ResourceSubscriber<*>?): CompositeDisposable {
        if (compositeDisposable?.size() == 0) initCompositeSubscription()

        subscription?.apply {
            if (compositeDisposable?.size() != 0) compositeDisposable?.remove(subscription)
            compositeDisposable?.add(subscription)
        }

        return compositeDisposable as CompositeDisposable
    }

}