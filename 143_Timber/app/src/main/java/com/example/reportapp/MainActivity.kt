package com.example.reportapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCrash = findViewById<Button>(R.id.btn_crash)
        Timber.d("Test Debug")
        btnCrash.setOnClickListener {
            // Kode di atas digunakan untuk membuat
            // log/catatan yang dapat Anda selipkan pada aplikasi Anda,
            FirebaseCrashlytics.getInstance().log("Clicked on Button")

            Timber.i("info: Tombol Terklik")
            Timber.tag("tombol").d("tertekan")

            // Mengirim data ke report
            FirebaseCrashlytics.getInstance().setCustomKey("str_key", "some_data")
            try {
                throw java.lang.RuntimeException("Test Crash")
            }catch (e:Exception){
                Timber.e("Test non Fatal Exception ${e.message}")
            }
        }
    }
}