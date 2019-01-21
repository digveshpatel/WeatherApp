package com.android.weathertask.presantation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        configureDagger()
        viewModelSetUp()
        viewSetUp()
    }

    abstract fun getLayout(): Int

    abstract fun viewModelSetUp()

    abstract fun viewSetUp()

    fun configureDagger() {
        AndroidInjection.inject(this)
    }

}