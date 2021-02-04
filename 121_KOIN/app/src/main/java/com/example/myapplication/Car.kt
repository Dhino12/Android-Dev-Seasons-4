package com.example.myapplication

class Car(private val engine: Engine) {
    fun start(){
        engine.start()
        println("Mobil Jalan")
    }
}