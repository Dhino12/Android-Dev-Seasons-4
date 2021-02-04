package com.example.myapplication.data

import com.example.myapplication.domain.IMessageRepository
import com.example.myapplication.domain.MessageEntity

class MessageRepository(private val messageDataSource:IMessageDataSource):IMessageRepository {
    override fun getWelcomeMessage(name: String): MessageEntity =
        messageDataSource.getMessageFromSource(name)
}

/*
* class Repository yang merupakan turunan dari kelas IMessageRepository yang ada di package Domain.
* Kelas ini akan mengatur datasource yang akan diberikan kepada Use Case.
* */