package ru.konkatenazia.tgmusicbotkt.services.keyboards

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Service
class KeyboardService {
    fun getMainKeyboard(chatId: Long): SendMessage {
        val message = SendMessage()
        message.setChatId(chatId)
        message.text = "Основная клавиатура"

        val inlineKeyboardButton = InlineKeyboardButton()
        inlineKeyboardButton.text = "Показать музыкальные категории"
        inlineKeyboardButton.callbackData = "getMusicCategories"

        val firstRow: List<InlineKeyboardButton> = mutableListOf(inlineKeyboardButton)
        val rowsInLine: List<List<InlineKeyboardButton>> = mutableListOf(firstRow)
        val markupInline = InlineKeyboardMarkup(rowsInLine)

        message.replyMarkup = markupInline
        return message
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

        val row1 = listOf(
            rockButton,
            popButton,
            jazzButton
        )

        val indieButton = InlineKeyboardButton()
        indieButton.text = "Indie"
        indieButton.callbackData = "getIndieCategory"

        val rapButton = InlineKeyboardButton()
        rapButton.text = "Rap"
        rapButton.callbackData = "getRapCategory"

        val bluesButton = InlineKeyboardButton()
        bluesButton.text = "Blues"
        bluesButton.callbackData = "getBluesCategory"

        val row2 = listOf(
            indieButton,
            rapButton,
            bluesButton
        )

        val rows = listOf(
            row1,
            row2
        )

        val keyboardMarkup = InlineKeyboardMarkup(rows)
        val message = SendMessage()
        message.setChatId(chatId)
        message.text = "Выберите категорию"
        message.replyMarkup = keyboardMarkup
        return message
    }
}