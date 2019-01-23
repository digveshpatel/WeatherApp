package com.android.weathertask.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.mykotlinapplication.presantation.base.ViewModelFactory
import com.android.weathertask.di.key.ViewModelKey
import com.android.weathertask.presantation.main.LocationViewModel
import com.android.weathertask.presantation.weather.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun provideWeatherViewModel(weatherViewModel: WeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun provideLocationViewModel(locationViewModel: LocationViewModel): ViewModel

}