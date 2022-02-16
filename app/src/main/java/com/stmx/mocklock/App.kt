package com.stmx.mocklock

import android.app.Application
import android.content.Context
import com.stmx.mocklock.di.AppComponent
import com.stmx.mocklock.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }
