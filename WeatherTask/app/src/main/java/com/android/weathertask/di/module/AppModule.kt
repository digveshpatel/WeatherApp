package com.android.weathertask.di.module

import android.content.Context
import com.android.weathertask.WeatherApplication
import com.android.weathertask.data.LocationDataRepository
import com.android.weathertask.data.WeatherDataRepository
import com.android.weathertask.domain.repository.LocationRepository
import com.android.weathertask.domain.repository.WeatherRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(weatherApplication: WeatherApplication): Context {
        return weatherApplication
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherDataRepository: WeatherDataRepository): WeatherRepository {
        return weatherDataRepository
    }

    @Provides
    @Singleton
    fun provideLocationRepository(locationDataRepository: LocationDataRepository): LocationRepository {
        return locationDataRepository
    }

}