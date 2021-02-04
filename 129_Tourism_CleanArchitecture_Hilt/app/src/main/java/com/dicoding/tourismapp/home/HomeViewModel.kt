package com.dicoding.tourismapp.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.tourismapp.core.data.TourismRepository
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import javax.inject.Inject

class HomeViewModel @ViewModelInject constructor(TourismUseCase: TourismUseCase) : ViewModel() {

    val tourism = TourismUseCase.getAllTourism().asLiveData()

}

