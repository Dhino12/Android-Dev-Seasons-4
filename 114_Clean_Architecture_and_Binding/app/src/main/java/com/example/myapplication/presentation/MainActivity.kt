package com.example.myapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // menggunakan Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        viewModel.setName("Dhino Rahmad")
        viewModel.message.observe(this, Observer {
            binding.tvWelcome.text = it.welcomeMessage
        })
    }

}

/*
* ActivityMainBinding merupakan kelas yang dibuat secara otomatis
* oleh ViewBinding untuk mem-binding komponen di dalam activity_main.xml.
* */

/*
* Untuk mengakses komponen dengan id tv_welcome pada activity_main.xml dapat dilakukan dengan cara binding.
* tvWelcome(bentuk camel case dari tv_welcome). Anda juga dapat menggunakan cara yang sama untuk mengakses komponen lainnya.
* */