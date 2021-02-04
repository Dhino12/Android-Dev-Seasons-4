package com.example.myapplication.di

import com.example.myapplication.data.IMessageDataSource
import com.example.myapplication.data.MessageDataSource
import com.example.myapplication.data.MessageRepository
import com.example.myapplication.domain.IMessageRepository
import com.example.myapplication.domain.MessageInteractor
import com.example.myapplication.domain.MessageUseCase

object Injection {
    fun provideUseCase():MessageUseCase{
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository)
    }

    private fun provideRepository():IMessageRepository{
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource():IMessageDataSource{
        return  MessageDataSource()
    }
}

// ViewModelFactory akan membutuhkan class MessageUseCase maka kita membutuhkan class untuk
// inject MessageUseCase ke dalam ViewModelFactory
// membuat sebuah kelas untuk inject MessageUseCase ke dalam ViewModelFactory.