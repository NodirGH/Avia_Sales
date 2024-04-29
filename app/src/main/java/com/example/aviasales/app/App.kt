package com.example.aviasales.app

import android.app.Application
import android.content.Context
import com.example.data.local.AppPreferences
import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
//class App : Application() {
//
//    companion object{
//        lateinit var application: Context
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        application = this
//        AppPreferences.init(this)
//    }
//}