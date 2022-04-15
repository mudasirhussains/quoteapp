package com.example.dailyqoutesmotivational.utils

import android.app.Application
import android.content.Context
import com.example.dailyqoutesmotivational.utils.DaggerBaseComponent.*

open class BaseApplication : Application() {

    val baseComponent: BaseComponent = builder()
        .networkModule(NetworkModule())
        .build()


    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

    companion object {
        lateinit var baseApplication: Context

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

}
