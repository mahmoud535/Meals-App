package com.example.mealsapp.presentation

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.EntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}