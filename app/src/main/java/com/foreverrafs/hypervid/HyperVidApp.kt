package com.foreverrafs.hypervid

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.time.LocalDate

class HyperVidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}