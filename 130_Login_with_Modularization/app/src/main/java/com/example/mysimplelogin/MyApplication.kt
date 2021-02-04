package com.example.mysimplelogin

import android.app.Application
import com.example.mysimplelogin.di.AppComponent
import com.example.mysimplelogin.di.DaggerAppComponent

open class MyApplication :Application(){

    val appComponent:AppComponent by lazy {
        DaggerAppComponent.factory().create(
            applicationContext
        )
    }

}