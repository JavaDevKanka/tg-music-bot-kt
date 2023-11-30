package ru.konkatenazia.tgmusicbotkt.services.keyboards

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.konkatenazia.tgmusicbotkt.dto.enums.CallbackPrefix
import ru.konkatenazia.tgmusicbotkt.dto.enums.KeyboardContext
import ru.konkatenazia.tgmusicbotkt.model.Music
import ru.konkatenazia.tgmusicbotkt.reository.MusicRepository
import ru.konkatenazia.tgmusicbotkt.reository.SongRepository
import ru.konkatenazia.tgmusicbotkt.services.MessageProcessingService
import ru.konkatenazia.tgmusicbotkt.services.basebot.BotHeart
import java.util.*

@Service
class KeyboardService(
    val messageProcessingService: MessageProcessingService,
    val botHeart: BotHeart,
    val songRepository: SongRepository,
    val musicRepository: MusicRepository
) {

    private var lastSongId: UUID? = null
    private var lastAuthorId: Long? = null
    fun getLanguageSelectionKeyboard(chatId: Long): SendMessage {
        val russianButton = InlineKeyboardButton()
        russianButton.text = "Русский"
        russianButton.callbackData = "selectRussian"

        val englishButton = InlineKeyboardButton()
        englishButton.text = "English"
        englishButton.callbackData = "selectEnglish"

        val row = listOf(russianButton, englishButton)
        val rows = listOf(row)

        val keyboardMarkup = InlineKeyboardMarkup(rows)
        val message = SendMessage()
        message.setChatId(chatId)
        message.text = "Выберите язык песни"
        message.replyMarkup = keyboardMarkup
        return message
    }

    fun getLetterKeyboard(chatId: Long, dataFromDb: List<String>, keyboardContext: KeyboardContext): SendMessage {
        val alphabet: String = getHavingLetters(chatId, dataFromDb, keyboardContext)
        val rows = mutableListOf<List<InlineKeyboardButton>>()

        for (i in alphabet.indices step 5) {
            val row = mutableListOf<InlineKeyboardButton>()
            val endIndex = minOf(i + 5, alphabet.length)

            for (j in i until endIndex) {
                val letterButton = InlineKeyboardButton()
                letterButton.text = alphabet[j].toString()
                letterButton.callbackData = "getLetterCategory:${alphabet[j]}"
                row.add(letterButton)
            }

            rows.add(row)
        }

        val keyboardMarkup = InlineKeyboardMarkup(rows)
        val message = SendMessage()
        message.setChatId(chatId)
        message.text = "Выберите первую букву имени автора"
        message.replyMarkup = keyboardMarkup
        return message
    }

    fun getAuthorLetterKeyboard(
        chatId: Long,
        page: Int,
        pageSize: Int,
        firstLetters: String,
        callbackPrefix: CallbackPrefix
    ): SendMessage {
        val pageable = PageRequest.of(page - 1, pageSize, Sort.by(KeyboardContext.AUTHOR.context))
        val rows = mutableListOf<List<InlineKeyboardButton>>()

        val pages = if (lastAuthorId != null) {
            musicRepository.findAllByAuthorStartingWithIgnoreCaseAndIdGreaterThan(
                firstLetters,
                lastAuthorId!!,
                pageable
            )
        } else {
            musicRepository.findAllByAuthorStartingWithIgnoreCase(firstLetters, pageable)
        }

        for (elem in pages) {
            val authorButton = InlineKeyboardButton()
            authorButton.text = elem.author
            authorButton.callbackData = callbackPrefix.prefix + elem.id
            rows.add(listOf(authorButton))
        }

        lastAuthorId = if (pages.isNotEmpty()) {
            pages.last().id
        } else {
            null
        }

        val keyboardMarkup = InlineKeyboardMarkup(rows)
        val message = SendMessage()
        message.setChatId(chatId)
        message.text = "Выберите автора"
        message.replyMarkup = keyboardMarkup
        return message
    }

    fun getSongLetterKeyboard(
        chatId: Long,
        page: Int,
        pageSize: Int,
        firstLetters: String,
        callbackPrefix: CallbackPrefix
    ): SendMessage {
        val pageable = PageRequest.of(page - 1, pageSize, Sort.by(KeyboardContext.SONG.context))
        val songRows = mutableListOf<List<InlineKeyboardButton>>()

        val pages = if (lastSongId != null) {
            songRepository.findAllBySongNameStartingWithAndIdGreaterThan(
                firstLetters,
                lastSongId!!,
                pageable
            )
        } else {
            songRepository.findAllBySongNameStartingWith(firstLetters, pageable)
        }

        for (elem in pages) {
            val authorButton = InlineKeyboardButton()
            authorButton.text = elem.songName
            authorButton.callbackData = callbackPrefix.prefix + elem.id
            songRows.add(listOf(authorButton))
        }

        lastSongId = if (pages.isNotEmpty()) {
            pages.last().id
        } else {
            null
        }

        val keyboardMarkup = InlineKeyboardMarkup(songRows)
        val message = SendMessage()
        message.setChatId(chatId)
        message.text = "Выберите первую букву названия Песни"
        message.replyMarkup = keyboardMarkup
        return message
    }

    fun getHavingLetters(chatId: Long, dataFromDb: List<String>, keyboardContext: KeyboardContext): String {
        val finishRusLetters = StringBuilder()
        val finishEnLetters = StringBuilder()
        for (letter in dataFromDb) {
            if (messageProcessingService.isEnglishLayout(letter)) {
                finishEnLetters.append(letter.uppercase())
            } else {
                finishRusLetters.append(letter.uppercase())
            }
        }
        if (keyboardContext.name == "EN" && finishEnLetters.isNotEmpty()) {
            return finishEnLetters.toString()
        }
        if (keyboardContext.name == "RU" && finishRusLetters.isNotEmpty()) {
            return finishRusLetters.toString()
        }
        botHeart.sendMessage(
            chatId,
            "Данные, первая буква которых начинающиеся на ${keyboardContext.name} языке в БД не найдены!"
        )
        throw RuntimeException("Данные, первая буква которых начинающиеся на ${keyboardContext.name} языке в БД не найдены!")
    }

    fun getMusicCategoriesKeyboard(chatId: Long): SendMessage {
        val rockButton = InlineKeyboardButton()
        rockButton.text = "Rock"
        rockButton.callbackData = "getRockCategory"

        val popButton = InlineKeyboardButton()
        popButton.text = "Pop"
        popButton.callbackData = "getPopCategory"

        val jazzButton = InlineKeyboardButton()
        jazzButton.text = "Jazz"
        jazzButton.callbackData = "getJazzCategory"

        val row1 = listOf(rockButton, popButton, jazzButton)

        val indieButton = InlineKeyboardButton()
        indieButton.text = "Indie"
        indieButton.callbackData = "getIndieCategory"

        val rapButton = InlineKeyboardButton()
        rapButton.text = "Rap"
        rapButton.callbackData = "getRapCategory"

        val bluesButton = InlineKeyboardButton()
        bluesButton.text = "Blues"
        bluesButton.callbackData = "getBluesCategory"

        val row2 = listOf(indieButton, rapButton, bluesButton)

        val rows = listOf(row1, row2)

        val keyboardMarkup = InlineKeyboardMarkup(rows)
        val message = SendMessage()
        message.setChatId(chatId)
        message.text = "Выберите категорию"
        message.replyMarkup = keyboardMarkup
        return message
    }
}