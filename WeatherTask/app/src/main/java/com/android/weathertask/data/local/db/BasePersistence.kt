package com.android.weathertask.data.local.db

import android.content.Context
import androidx.room.Room

abstract class BasePersistence(private val context: Context) {

    @Volatile
    private var database: LocalDatabase? = null

    var db: LocalDatabase = getInstance(context)
        get() = database ?: getInstance(context)

    private fun getInstance(context: Context) =
        database ?: synchronized(this) {
            database ?: this.buildDatabase(context).also { database = it }
        }

    private fun buildDatabase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java, getDbName()
        ).build()

    abstract fun getDbName(): String
}
