package ru.konkatenazia.tgmusicbotkt.services.basebot

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.send.SendAudio
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.konkatenazia.tgmusicbotkt.config.BotConfig
import javax.annotation.PostConstruct

@Component
class BotHeart(
    private val botConfig: BotConfig,
    private val applicationEventPublisher: ApplicationEventPublisher
) : TelegramLongPollingBot() {

    val logger: Logger = LoggerFactory.getLogger(BotHeart::class.java)

    @PostConstruct
    fun setCommands() {
        val commands: List<BotCommand> = listOf(
            BotCommand("/show", "Отобразить клавиатуру"),
            BotCommand("/music", "Получить случайную музыку")
        )
        execute(SetMyCommands(commands, BotCommandScopeDefault(), null))
    }

    override fun onUpdateReceived(update: Update?) {
        if (update != null) {
            applicationEventPublisher.publishEvent(update)
        }
    }

    fun sendMessage(message: SendMessage) {
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            logger.info("Не удалось отправить сообщение \"${message.text}\"")
        }

    }

    fun sendMessage(chatId: Long, message: String) {
        val sendMessage = SendMessage()
        sendMessage.setChatId(chatId)
        sendMessage.text = message
        try {
            execute(sendMessage)
        } catch (e: TelegramApiException) {
            logger.info("Не удалось отправить сообщение \"$message\"")
        }

    }

    fun sendMessage(chatId: Long, message: String, messageId: Int) {
        val sendMessage = SendMessage()
        sendMessage.replyToMessageId = messageId
        sendMessage.setChatId(chatId)
        sendMessage.text = message
        try {
            execute(sendMessage)
        } catch (e: TelegramApiException) {
            logger.info("Не удалось отправить сообщение \"$message\"")
        }

    }

    fun sendMessage(message: SendMessage, messageId: Int) {
        message.replyToMessageId = messageId
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            logger.info("Не удалось отправить сообщение \"${message.text}\"")
        }

    }


    fun sendAudio(chatId: Long, inputFile: InputFile) {
        val sendAudio = SendAudio()
        sendAudio.setChatId(chatId)
        sendAudio.audio = inputFile
        try {
            execute(sendAudio)
        } catch (e: TelegramApiException) {
            logger.info("Не удалось отправить файл \"${inputFile.mediaName}\"")
        }

    }

    override fun getBotUsername(): String {
        return botConfig.botName
    }

    override fun getBotToken(): String {
        return botConfig.botToken
    }
}