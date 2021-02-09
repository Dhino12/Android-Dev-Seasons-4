package com.example.cores.domain.usecase

import com.example.cores.data.Resource
import com.example.cores.domain.model.Tourism
import com.example.cores.domain.repository.ITourismRepository
import kotlinx.coroutines.flow.Flow

class TourismInteractor (private val tourismRepository: ITourismRepository):TourismUseCase{
    override fun getAllTourism(): Flow<Resource<List<Tourism>>> =
        tourismRepository.getAllTourism()

    override fun getFavoriteTourism(): Flow<List<Tourism>> =
        tourismRepository.getFavoriteTourism()

    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) =
        tourismRepository.setFavoriteTourism(tourism, state)
}