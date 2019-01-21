package com.android.weathertask

import android.app.Activity
import android.app.Application
import com.android.weathertask.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class WeatherApplication :Application(), HasActivityInjector {

    @Inject
    lateinit var diapacheAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun activityInjector(): AndroidInjector<Activity> {
        return diapacheAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        // init dagger here
        AppInjector.init(this)


    }
}