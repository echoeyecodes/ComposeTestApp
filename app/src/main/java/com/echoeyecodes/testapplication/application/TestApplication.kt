package com.echoeyecodes.testapplication.application

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object{
        private var application: Application? = null
        private set
        val context: Context
        get() = application!!.applicationContext
    }
}