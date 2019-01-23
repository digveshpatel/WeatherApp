package com.android.weathertask

import android.app.Activity
import android.app.Application
import com.android.weathertask.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class WeatherApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var diapacheAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = diapacheAndroidInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}