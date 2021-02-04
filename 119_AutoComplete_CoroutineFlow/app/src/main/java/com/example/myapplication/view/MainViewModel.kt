package com.example.myapplication.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.myapplication.network.ApiConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel :ViewModel(){

    private val accessToken = "pk.eyJ1IjoibW9jaGExMiIsImEiOiJja2o1dHQ0N2IzYjNkMnFydzBmYjUzdzd1In0.bfc1PUK0YxJ-2RClYz9I9w"
    val queryClient = BroadcastChannel<String>(Channel.CONFLATED)

    val searchResult = queryClient.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            ApiConfig.provideApiService().getCountry(it, accessToken).features
        }
        .asLiveData()

}

/*
* BroadcastChannel digunakan untuk membuat channel yang bertujuan
untuk berkomunikasi antar coroutine, seperti mengirim dan menerima value.
* CONFLATED, artinya hanya nilai terakhir saja yang disimpan, sedangkan untuk
nilai yang sebelumnya dihiraukan.
* */