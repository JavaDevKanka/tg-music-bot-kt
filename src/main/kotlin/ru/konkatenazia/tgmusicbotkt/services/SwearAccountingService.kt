package ru.konkatenazia.tgmusicbotkt.services

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.User
import ru.konkatenazia.tgmusicbotkt.mapper.SwearAccountingMapper
import ru.konkatenazia.tgmusicbotkt.model.SwearAccounting
import ru.konkatenazia.tgmusicbotkt.reository.ChatUserRepository
import ru.konkatenazia.tgmusicbotkt.reository.SwearAccountingRepository

@Service
class SwearAccountingService(
    val swearAccountingRepository: SwearAccountingRepository,
    val swearAccountingMapper: SwearAccountingMapper,
    val chatUserRepository: ChatUserRepository
) {
    val logger = LoggerFactory.getLogger(SwearAccountingService::class.java)
    fun saveSwearAccounting(messageText: String, user: User, chatId: Long, isGroupChat: Boolean, isActive: Boolean): SwearAccounting {
        val chatUser = chatUserRepository.findById(user.id)
        if (chatUser.isPresent) {
            val swear = swearAccountingMapper.toSwearAccounting(chatUser.get(), messageText, chatId, isGroupChat, isActive)
            return swearAccountingRepository.save(swear)
        }
        logger.info("Пользователь с id ${user.id} не найден в БД, ругательство не сохранено")
        throw RuntimeException("")
    }
}