package com.example.tourismapp.detail

import androidx.lifecycle.ViewModel
import com.example.cores.domain.model.Tourism
import com.example.cores.domain.usecase.TourismUseCase

class DetailTourismViewModel(private val tourismUseCase: TourismUseCase) : ViewModel() {
    fun setFavoriteTourism(tourism: Tourism, newStatus:Boolean) = tourismUseCase.setFavoriteTourism(tourism, newStatus)
}

