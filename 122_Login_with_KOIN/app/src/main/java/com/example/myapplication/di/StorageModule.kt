package com.example.myapplication.di

import com.example.myapplication.SessionManager
import com.example.myapplication.UserRepository
import org.koin.dsl.module

val storageModule = module {
    factory {
        SessionManager(get())
    }
    single {
        UserRepository(get())
    }
}