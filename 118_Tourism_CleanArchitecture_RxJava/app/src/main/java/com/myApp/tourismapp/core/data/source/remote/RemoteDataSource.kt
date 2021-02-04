package com.myApp.tourismapp.core.data.source.remote

import android.util.Log
import com.myApp.tourismapp.core.data.source.remote.network.ApiResponse
import com.myApp.tourismapp.core.data.source.remote.network.ApiService
import com.myApp.tourismapp.core.data.source.remote.response.TourismResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService)
            }
    }

    // dataStream akan di emit secara terus menerus unlimited
    fun getAllTourism(): Flowable<ApiResponse<List<TourismResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<TourismResponse>>>()

        //get data from remote api
        val client = apiService.getList()
        client
            .subscribeOn(Schedulers.computation()) // membutuhkan proses CPU tinggi
            .observeOn(AndroidSchedulers.mainThread()) // menerima hasil pada mainThread
            .take(1)
            .subscribe( {response ->
                Log.e("RemoteDataSource",response.places.toString())
                val dataArray = response.places
                resultData.onNext(if( dataArray.isNotEmpty() ) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            },{ error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource",error.toString())
            } )

        //mengubah Subject menjadi Flowable kita menggunakan fungsi toFlowable.
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}

