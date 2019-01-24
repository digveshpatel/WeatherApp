package com.android.weathertask.presantation.base

import androidx.lifecycle.ViewModel
import com.android.weathertask.domain.UseCase

abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        getUseCases().forEach { it.clearAllSubscription() }
        super.onCleared()
    }

    abstract fun getUseCases(): List<UseCase<*, *>>

}