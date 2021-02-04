package com.myApp.tourismapp.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.myApp.tourismapp.core.domain.usecase.TourismUseCase

class HomeViewModel(tourismUseCase: TourismUseCase) : ViewModel() {

    //  convert data Flowable menjadi LiveData menggunakan LiveDataReactiveStreams
    val tourism = LiveDataReactiveStreams.fromPublisher(tourismUseCase.getAllTourism())

}

