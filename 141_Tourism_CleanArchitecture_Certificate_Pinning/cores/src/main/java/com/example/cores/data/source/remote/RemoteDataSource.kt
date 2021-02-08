package com.example.cores.data.source.remote

import android.util.Log
import com.example.cores.data.source.remote.network.ApiResponse
import com.example.cores.data.source.remote.network.ApiService
import com.example.cores.data.source.remote.response.TourismResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllTourism(): Flow<ApiResponse<List<TourismResponse>>> {

        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.places
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.places))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

