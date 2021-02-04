package com.example.mysimplelogin.di

import android.content.Context
import com.example.mysimplelogin.HomeActivity
import com.example.mysimplelogin.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context):AppComponent
    }

    fun inject(mainActivity:MainActivity)
    fun inject(homeActivity:HomeActivity)
}