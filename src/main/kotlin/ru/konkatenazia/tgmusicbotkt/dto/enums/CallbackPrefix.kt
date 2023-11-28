package ru.konkatenazia.tgmusicbotkt.dto.enums

enum class CallbackPrefix(val prefix: String) {
    AUTHOR_LETTER_CALLBACK("getAuthor:"),
    SONG_NAME_LETTER_CALLBACK("getSong:");
}