package com.example.myapplication

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

// === Kode Lengkapnua
val appModule = module { // membuat module koin

    factory {
        Engine()
    }
    factory {
        Car(get())
    }
    single { // agar tidak membuat instance baru
        Car(get())
    }

//    scope(named("Module A")){ // agar object dapat diakses oleh bbrpa module
//        factory {
//            Engine()
//        }
//        scoped {
//            Car(get())
//        }
//    }
}

open class MyApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin { // menggunakan koin di semua class

            androidContext(this@MyApplication ) // menambahkan context

            modules(appModule) // memasukan module dengan modules()

            // val modulAScope = koin.createScope("myScopeId",named("Module A"))
            // moduleAScope.close() daur ulang scope
        }
    }
}
// ========================

class MainActivity : AppCompatActivity() {

    val engine :Engine by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val car = Car(engine)
        car.start()
    }

}