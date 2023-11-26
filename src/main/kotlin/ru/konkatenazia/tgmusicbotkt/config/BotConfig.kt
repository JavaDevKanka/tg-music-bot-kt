package ru.konkatenazia.tgmusicbotkt.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class BotConfig(
    @Value("\${telegram.bot-name}")
    val botName: String,
    @Value("\${telegram.bot-token}")
    val botToken: String
)
