package com.example.myapplication.data

import com.example.myapplication.domain.MessageEntity

interface IMessageDataSource {
    fun getMessageFromSource(neme:String):MessageEntity
}

// membuat interface untuk datasource yang merupakan template untuk implementasinya nanti.