package com.example.movieapp.root

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class RootApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}