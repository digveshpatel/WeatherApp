package com.android.weathertask.di.module

import androidx.lifecycle.ViewModelProvider
import com.android.mykotlinapplication.presantation.base.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory



}