package com.example.myapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.MessageEntity
import com.example.myapplication.domain.MessageUseCase

class MainViewModel(private val messageUseCase: MessageUseCase) : ViewModel() {
    private val _message = MutableLiveData<MessageEntity>()
    val message:LiveData<MessageEntity>
        get() = _message

    fun setName(name:String){
        _message.value = messageUseCase.getMessage(name)
    }
}

// membuat ViewModel yang akan mengambil data dari UseCase.