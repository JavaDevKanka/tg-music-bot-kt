package ru.konkatenazia.tgmusicbotkt.services.transmitter

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.konkatenazia.tgmusicbotkt.dto.enums.CallbackPrefix
import ru.konkatenazia.tgmusicbotkt.dto.enums.KeyboardContext
import ru.konkatenazia.tgmusicbotkt.reository.MusicRepository
import ru.konkatenazia.tgmusicbotkt.reository.SongRepository
import ru.konkatenazia.tgmusicbotkt.services.basebot.BotHeart
import ru.konkatenazia.tgmusicbotkt.services.keyboards.KeyboardService

@Service
class CallbackProcessor(
    val botHeart: BotHeart,
    val keyboardService: KeyboardService,
    val musicRepository: MusicRepository,
    val songRepository: SongRepository
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

            if (callbackData.equals("selectRussian")) {
                botHeart.sendMessage(keyboardService.getLetterKeyboard(chatId, musicRepository.getUniqueAuthorFirstLetters(), KeyboardContext.RU), messageId)
            }
            if (callbackData.equals("selectEnglish")) {
                botHeart.sendMessage(keyboardService.getLetterKeyboard(chatId, musicRepository.getUniqueAuthorFirstLetters(), KeyboardContext.EN), messageId)
            }

            if (musicRepository.getUniqueAuthorFirstLetters().contains(callbackData.last().toString())) {
                botHeart.sendMessage(keyboardService.getAuthorLetterKeyboard(chatId, 1, 10,
                    callbackData.last().toString(), CallbackPrefix.AUTHOR_LETTER_CALLBACK))
            }

//            if (songRepository.getUniqueSongNameFirstLetters().contains(callbackData.last().toString())) {
//                botHeart.sendMessage(keyboardService.getSongLetterKeyboard(chatId, 1, 10,
//                    callbackData.last().toString(), CallbackPrefix.SONG_NAME_LETTER_CALLBACK))
//            }

        }
    }
}