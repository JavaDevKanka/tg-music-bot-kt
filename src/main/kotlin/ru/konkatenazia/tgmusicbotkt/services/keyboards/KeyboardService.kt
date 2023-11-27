package ru.konkatenazia.tgmusicbotkt.services.keyboards

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.konkatenazia.tgmusicbotkt.dto.enums.KeyboardContext
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

    fun getSongPagedKeyboard(chatId: Long, page: Int, pageSize: Int, firstLetter: String): SendMessage {
        val pageable = PageRequest.of(page - 1, pageSize, Sort.by("author"))

        val authorsPage = if (lastAuthorId != null) {
            musicRepository.findAllByAuthorStartingWithAndIdGreaterThan(firstLetter, lastAuthorId!!, pageable)
        } else {
            musicRepository.findAllByAuthorStartingWith(firstLetter, pageable)
        }

        val rows = mutableListOf<List<InlineKeyboardButton>>()

        for (author in authorsPage) {
            val authorButton = InlineKeyboardButton()
            authorButton.text = author.author
            authorButton.callbackData = "getSong:${author.id}"
            rows.add(listOf(authorButton))
        }

        lastAuthorId = if (authorsPage.isNotEmpty()) {
            authorsPage.last().id
        } else {
            null
        }

        val keyboardMarkup = InlineKeyboardMarkup(rows)
        val message = SendMessage()
        message.setChatId(chatId)
        message.text = "Выберите первую букву"
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