package com.example.tourismapp.di

import com.example.cores.domain.usecase.TourismInteractor
import com.example.cores.domain.usecase.TourismUseCase
import com.example.tourismapp.detail.DetailTourismViewModel
import com.example.tourismapp.favorite.FavoriteViewModel
import com.example.tourismapp.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory <TourismUseCase>{ TourismInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailTourismViewModel(get()) }
}