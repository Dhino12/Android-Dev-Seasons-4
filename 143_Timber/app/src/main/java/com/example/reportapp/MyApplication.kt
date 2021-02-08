package com.example.reportapp

import android.app.Application
import timber.log.Timber

class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            // Tampilkan Log ketika debug
            Timber.plant(Timber.DebugTree())
        }else{
            // Tetap menampilkan log ketika di aplikasi release
                // Optional
            Timber.plant(ReleaseTree())
        }
    }
}