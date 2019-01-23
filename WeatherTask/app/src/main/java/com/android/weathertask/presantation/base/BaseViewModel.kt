package com.android.weathertask.presantation.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        clearSubscriptions()
        super.onCleared()
    }

    abstract fun clearSubscriptions()

}