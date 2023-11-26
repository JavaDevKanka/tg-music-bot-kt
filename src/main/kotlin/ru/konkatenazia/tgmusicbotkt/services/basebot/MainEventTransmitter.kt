package ru.konkatenazia.tgmusicbotkt.services.basebot

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.konkatenazia.tgmusicbotkt.services.MusicService
import ru.konkatenazia.tgmusicbotkt.services.keyboards.KeyboardService
import ru.konkatenazia.tgmusicbotkt.services.transmitter.CallbackProcessor
import ru.konkatenazia.tgmusicbotkt.services.transmitter.MessageProcessor

@Component
class MainEventTransmitter(
    val botHeart: BotHeart,
    val keyboardService: KeyboardService,
    val callbackProcessor: CallbackProcessor,
    val messageProcessor: MessageProcessor,
    val musicService: MusicService
) {
    val logger = LoggerFactory.getLogger(MainEventTransmitter::class.java)

    @EventListener
    fun processUpdate(update: Update) {
        if (update.hasCallbackQuery()) {
            callbackProcessor.processCallback(update.callbackQuery)
        }
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chat.id
            messageProcessor.processMessage(message)

            if (update.message.hasText()) {
                val messageText = message.text
                val commands = HashMap<String, Runnable>()
                commands.put("/show") { botHeart.sendMessage(keyboardService.getMainKeyboard(chatId)) }
                commands.put("/music") { botHeart.sendAudio(chatId, musicService.getRandomMusic()) }
                for (command in commands.keys) {
                    if (messageText.contains(command)) {
                        commands[command]?.run()
                        break
                    }
                }
            }
        }
    }

}