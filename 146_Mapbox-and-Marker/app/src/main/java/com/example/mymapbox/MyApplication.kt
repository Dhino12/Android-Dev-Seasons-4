package com.example.mymapbox

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

open class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(applicationContext, getString(R.string.access_token_api))
    }
}