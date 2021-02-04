package com.example.tourismapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cores.domain.usecase.TourismUseCase

class HomeViewModel(TourismUseCase: TourismUseCase) : ViewModel() {

    val tourism = TourismUseCase.getAllTourism().asLiveData()

}

