package com.android.example.thepokedex

import android.app.Application

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