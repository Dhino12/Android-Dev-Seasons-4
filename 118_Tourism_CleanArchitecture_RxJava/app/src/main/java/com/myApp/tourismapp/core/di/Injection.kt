package com.myApp.tourismapp.core.di

import android.content.Context

import com.myApp.tourismapp.core.data.source.local.LocalDataSource
import com.myApp.tourismapp.core.data.source.local.room.TourismDatabase

import com.myApp.tourismapp.core.data.TourismRepository
import com.myApp.tourismapp.core.data.source.remote.RemoteDataSource
import com.myApp.tourismapp.core.data.source.remote.network.ApiConfig
import com.myApp.tourismapp.core.domain.usecase.TourismInteractor
import com.myApp.tourismapp.core.domain.usecase.TourismUseCase
import com.myApp.tourismapp.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): TourismRepository {
        val database = TourismDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.tourismDao())
        val appExecutors = AppExecutors()

        return TourismRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideTourismUseCase(context: Context):TourismUseCase{
        val repository = provideRepository(context)
        return TourismInteractor(repository)
    }
}
