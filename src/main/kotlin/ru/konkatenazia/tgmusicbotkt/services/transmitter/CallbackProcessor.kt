package ru.konkatenazia.tgmusicbotkt.services.transmitter

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.konkatenazia.tgmusicbotkt.services.basebot.BotHeart
import ru.konkatenazia.tgmusicbotkt.services.keyboards.KeyboardService

@Service
class CallbackProcessor(
    val botHeart: BotHeart,
    val keyboardService: KeyboardService
) {
    fun processCallback(callback: CallbackQuery) {
        if (callback.message.hasText()) {
            val message = callback.message
            val callbackData = callback.data
            val chatId = message.chatId
            val messageId = message.messageId

            if (callbackData.equals("getMusicCategories")) {
                botHeart.sendMessage(keyboardService.getMusicCategoriesKeyboard(chatId), messageId)
            }


            //Блок кнопок
            if (callbackData == "getRockCategory") {
            }
            if (callbackData == "getPopCategory") {
            }
            if (callbackData == "getJazzCategory") {
            }
            if (callbackData == "getIndieCategory") {
            }
            if (callbackData == "getRapCategory") {
            }
            if (callbackData == "getBluesCategory") {
            }
        }
    }
}