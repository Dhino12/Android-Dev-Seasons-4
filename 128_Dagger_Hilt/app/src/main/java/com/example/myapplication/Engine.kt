package com.example.myapplication

import android.content.Context

class Engine(val context:Context) {

    fun start(){
        println("engine running $context")
    }
}