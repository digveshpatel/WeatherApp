package com.android.weathertask.di.component

import com.android.weathertask.WeatherApplication
import com.android.weathertask.di.module.ActivityModule
import com.android.weathertask.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class])
interface AppComponent {
    fun inject(application: WeatherApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: WeatherApplication): Builder

        fun build(): AppComponent
    }
}