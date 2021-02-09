package com.example.cores.domain.repository

import com.example.cores.data.Resource
import com.example.cores.domain.model.Tourism
import kotlinx.coroutines.flow.Flow

interface ITourismRepository {

    fun getAllTourism():Flow<Resource<List<Tourism>>>

    fun getFavoriteTourism():Flow<List<Tourism>>

    fun setFavoriteTourism(tourism:Tourism, state:Boolean)
}