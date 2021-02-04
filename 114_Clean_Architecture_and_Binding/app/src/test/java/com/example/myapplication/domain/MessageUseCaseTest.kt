package com.example.myapplication.domain

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MessageUseCaseTest {

    private lateinit var messageUseCase:MessageUseCase

    @Mock private lateinit var messageRepository:IMessageRepository

    @Before
    fun setup(){
        messageUseCase = MessageInteractor(messageRepository)
        val dummyMessage = MessageEntity("Hello $NAME ! Welcome to Clean Architecture")
        `when`(messageRepository.getWelcomeMessage(NAME)).thenReturn(dummyMessage)
    }

    @Test
    fun `should get data from repository`(){
        messageUseCase.getMessage(NAME)
        verify(messageRepository).getWelcomeMessage(NAME)
        verifyNoMoreInteractions(messageRepository)
    }

    companion object{
        const val NAME = "Dhino Rahmad"
    }

}

/*
* verifyNoMoreInteractions untuk memastikan semua interaksi sudah di-verify.
* Fungsi ini akan membuat test gagal jika ada interaksi yang belum di-verify.
* */