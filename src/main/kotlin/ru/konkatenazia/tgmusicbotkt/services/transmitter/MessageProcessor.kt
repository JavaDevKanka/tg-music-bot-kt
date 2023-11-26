package ru.konkatenazia.tgmusicbotkt.services.transmitter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Message
import ru.konkatenazia.tgmusicbotkt.services.MessageProcessingService
import ru.konkatenazia.tgmusicbotkt.services.SwearAccountingService
import ru.konkatenazia.tgmusicbotkt.services.UserService
import ru.konkatenazia.tgmusicbotkt.services.basebot.BotHeart

@Service
class MessageProcessor(
    val messageProcessingService: MessageProcessingService,
    val botHeart: BotHeart,
    val swearAccountingService: SwearAccountingService,
    val userService: UserService
) {
    val logger = LoggerFactory.getLogger(MessageProcessor::class.java)
    fun processMessage(message: Message) {
        if (message.text != null) {
            val user = message.from
            val chatId: Long = message.chatId
            val messageText = message.text
            val messageId = message.messageId
            if (!userService.isUserSaved(user)) {
                userService.saveUser(user)
            }

            if (messageProcessingService.checkForBadWords(messageText)) {
                if (message.chat.isSuperGroupChat || message.chat.isGroupChat) {
                    swearAccountingService.saveSwearAccounting(
                        messageText,
                        user,
                        chatId,
                        isActive = true,
                        isGroutChat = true
                    )
                }
            }

            if (messageProcessingService.isEnglishLayout(messageText) && !messageProcessingService.isEnglishWord(messageText) && !messageText.startsWith("http")) {
                logger.info("Тут отправляются переведенные кракозябры, текст был - \"$messageText\"")
                botHeart.sendMessage(chatId, messageProcessingService.invertKeyboardLayout(messageText), messageId)
            }
        }
    }
}