package com.android.weathertask.di.module

import com.android.weathertask.presantation.locations.LocationsFragment
import com.android.weathertask.presantation.weather.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeLocationsListFragment(): LocationsFragment

    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment(): WeatherFragment

}
