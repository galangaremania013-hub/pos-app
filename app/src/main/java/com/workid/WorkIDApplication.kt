package com.workid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WorkIDApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
