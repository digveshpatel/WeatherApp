package com.android.mykotlinapplication.presantation.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        destroyObservable()
        super.onCleared()
    }

    abstract fun destroyObservable()



}