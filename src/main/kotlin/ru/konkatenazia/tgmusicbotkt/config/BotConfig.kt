package ru.konkatenazia.tgmusicbotkt.config

import org.springframework.beans.factory.annotation.Value

data class BotConfig(
    @Value("\${telegram.bot-name}")
    val botName: String,
    @Value("\${telegram.bot-token}")
    val botToken: String
)
