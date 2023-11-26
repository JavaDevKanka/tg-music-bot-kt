package ru.konkatenazia.tgmusicbotkt.dto.word

data class PhoneticDTO(
    val text: String,
    val audio: String,
    val sourceUrl: String,
    val licence: LicenseDTO
)
