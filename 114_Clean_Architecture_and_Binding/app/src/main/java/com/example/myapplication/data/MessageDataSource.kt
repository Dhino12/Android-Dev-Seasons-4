package com.example.myapplication.data

import com.example.myapplication.domain.MessageEntity

class MessageDataSource : IMessageDataSource {
    override fun getMessageFromSource(neme: String): MessageEntity =
        MessageEntity("Hello $neme ! Welcome to Clean Architecture")
}

// membuat implementasi datasource yang merupakan sumber data message yang akan didapat.