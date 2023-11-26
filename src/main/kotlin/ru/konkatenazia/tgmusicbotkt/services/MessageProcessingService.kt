package ru.konkatenazia.tgmusicbotkt.services

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.konkatenazia.tgmusicbotkt.reository.SwearWordRepository

@Service
class MessageProcessingService(
    val swearWordRepository: SwearWordRepository
) {
    val logger = LoggerFactory.getLogger(MessageProcessingService::class.java)
    fun checkForBadWords(messageText: String): Boolean {
        val words = messageText.lowercase().split("[,\\s]+")
        logger.info("Плохое слово в предложении -  \"$words\"")
        return swearWordRepository.existsByWordInIgnoreCase(words)
    }

    fun invertKeyboardLayout(messageText: String): String {
        return ""
    }

}