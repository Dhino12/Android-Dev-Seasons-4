package com.myApp.tourismapp.core.domain.repository

import com.myApp.tourismapp.core.data.Resource
import com.myApp.tourismapp.core.domain.model.Tourism
import io.reactivex.Flowable

interface ITourismRepository {

    fun getAllTourism():Flowable<Resource<List<Tourism>>>

    fun getFavoriteTourism():Flowable<List<Tourism>>

    fun setFavoriteTourism(tourism:Tourism, state:Boolean)
}