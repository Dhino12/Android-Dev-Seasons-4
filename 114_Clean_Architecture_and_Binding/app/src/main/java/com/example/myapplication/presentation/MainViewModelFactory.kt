package com.example.myapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.di.Injection
import com.example.myapplication.domain.MessageUseCase
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val messageUseCase: MessageUseCase) :ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var instance:MainViewModelFactory? = null
        fun getInstance():MainViewModelFactory =
            instance ?: synchronized(this){
                instance ?: MainViewModelFactory(Injection.provideUseCase())
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(messageUseCase) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
        }
    }
}

/*
*  ViewModelFactory digunakan untuk menginisiasi ViewModel yang memiliki parameter.
* */