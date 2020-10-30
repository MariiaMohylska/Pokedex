package com.android.example.thepokedex

import android.app.Application
import com.android.example.thepokedex.presentation.di.AppComponent
import com.android.example.thepokedex.presentation.di.DaggerAppComponent

class App: Application() {
    lateinit var appComponent: AppComponent

    companion object{
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}