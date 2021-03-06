package com.android.weathertask.di.module

import com.android.weathertask.presantation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideLocationActivity(): MainActivity

}