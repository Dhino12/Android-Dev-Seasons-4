package com.example.myapplication

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class AppModule{
    @Singleton
    @Provides
    fun provideEngine():Engine = Engine()
}

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context):AppComponent
    }

    fun inject(activity:MainActivity)
}

open class MyApplication : Application(){
    val appComponent:AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var engine:Engine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MyApplication).appComponent.inject(this)

        val engine = Engine()
        val car = Car(engine)
        car.start()
    }

}

