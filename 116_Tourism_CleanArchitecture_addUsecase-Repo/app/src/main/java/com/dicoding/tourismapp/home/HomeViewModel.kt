package com.dicoding.tourismapp.home

import androidx.lifecycle.ViewModel
import com.dicoding.tourismapp.core.data.TourismRepository
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase

class HomeViewModel(TourismUseCase: TourismUseCase) : ViewModel() {

    val tourism = TourismUseCase.getAllTourism()

}

