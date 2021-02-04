package com.example.myapplication.domain

interface IMessageRepository {
    fun getWelcomeMessage(name:String): MessageEntity
}

/*
*  class ini berisi proses bisnis yang akan dipanggil dari Use Case.
* */