package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity() {

    val viewModel :MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edPlace = findViewById<AutoCompleteTextView>(R.id.ed_place)
        edPlace.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    viewModel.queryClient.send(s.toString()) // untuk mengirimkan broadcast dari MainActivity
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        viewModel.searchResult.observe(this, Observer {
            placesItem ->

            val placesName = arrayListOf<String?>()

            placesItem.map{
                placesName.add(it.placeName)
            }

            val adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, placesName)
            adapter.notifyDataSetChanged()
            edPlace.setAdapter(adapter)
        })
    }
}

/*
*  lifecycleScope digunakan untuk membuat scope coroutine yang aware terhadap lifecycle.
* Sehingga ketika lifecycle dihapus -seperti dalam keadaan onPause/onStop-
* maka Coroutine juga akan dibersihkan. Alhasil, aplikasi terhindar dari memory leak.
* */