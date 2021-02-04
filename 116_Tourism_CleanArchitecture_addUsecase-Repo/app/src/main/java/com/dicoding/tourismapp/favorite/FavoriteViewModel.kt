package com.dicoding.tourismapp.favorite

import androidx.lifecycle.ViewModel
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase

class FavoriteViewModel(TourismUseCase: TourismUseCase) : ViewModel() {

    val favoriteTourism = TourismUseCase.getFavoriteTourism()

}

