package com.example.myapplication.domain

class MessageInteractor(private val messageRepository: IMessageRepository):MessageUseCase {
    override fun getMessage(name: String): MessageEntity {
        return messageRepository.getWelcomeMessage(name)
    }
}

// Class untuk Use Case.