package com.example.myapplication

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Car @Inject constructor(private val engine: Engine) {
    fun start(){
        engine.start()
        println("car running")
    }
}

/*
* Perlu diingat bahwa jika di dalam Module terdapat Singleton
maka Component juga harus ditambahkan scope yang sama. Karena itu lah di atas AppComponent
juga ditambahkan annotation @Singleton.
* */