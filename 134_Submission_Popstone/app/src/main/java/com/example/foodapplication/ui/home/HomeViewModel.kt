package com.example.foodapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.FoodUseCase

class HomeViewModel(foodUseCase: FoodUseCase) : ViewModel() {

    val cook = foodUseCase.getAllCooking().asLiveData()
    val article = foodUseCase.getAllArticle().asLiveData()
}