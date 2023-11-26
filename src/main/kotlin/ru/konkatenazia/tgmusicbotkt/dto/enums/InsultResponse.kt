package ru.konkatenazia.tgmusicbotkt.dto.enums

import java.util.Random

enum class InsultResponse(val response: String) {
    INSULT_1("А ну-ка не пизди тут оскорблениями !!!."),
    INSULT_2("Пожалуйста, будь вежлив в своих высказываниях."),
    INSULT_3("Зашем материшься младой члавек?."),
    INSULT_4("Можешь выразить свою точку зрения без использования оскорбительных слов?"),
    NEUTRAL_1("Ругаться плохо (-_-)"),
    NEUTRAL_2("Я принимаю твое выражение во внимание!, тикай с села!"),
    NEUTRAL_3("Валера, настало твое время \uD83D\uDD2B"),
    NEUTRAL_4("Мне жаль, что ты так выражаешься."),
    NEUTRAL_5("Твои оскорбления говорят больше о тебе, чем обо мне."),
    NEUTRAL_6("Неужели это лучшее, что ты можешь сказать?");

    companion object {
        private val random = Random()

        fun getRandomInsultResponse(): InsultResponse {
            return values()[random.nextInt(values().size)]
        }
    }

}