package ru.konkatenazia.tgmusicbotkt.services

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User

@Service
class SwearAccountingService {
    fun saveSwearAccounting(messageText: String, user: User, chatId: Long, isGroutChat: Boolean, isActive: Boolean) {
        TODO("Not yet implemented")
    }
}