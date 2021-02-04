package com.example.myapplication.domain

interface MessageUseCase {
    fun getMessage(name:String): MessageEntity
}

// abstract class yang akan menjadi basis dari semua Use Case.