package com.example.tourismapp.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.example.cores.data.Resource
import com.example.tourismapp.maps.databinding.ActivityMapsBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class MapsActivity : AppCompatActivity() {

    private val mapsViewModel:MapsViewModel by viewModel()
    private lateinit var binding:ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(mapsModule)

        supportActionBar?.title = "Tourism Maps"

        getTourismData()
    }

    private fun getTourismData(){
        mapsViewModel.tourism.observe(this) { tourism ->
            if(tourism != null){
                when(tourism){
                    is Resource.Loading -> binding.progressBars.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBars.visibility = View.GONE
                        binding.tvMaps.text = "This is map of ${tourism.data?.get(0)?.name}"
                    }
                    is Resource.Error -> {
                        binding.progressBars.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = tourism.message
                    }
                }
            }
        }
    }
}