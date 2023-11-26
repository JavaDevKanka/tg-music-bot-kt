package ru.konkatenazia.tgmusicbotkt.dto.word

data class PhoneticDTO(
    private val text: String,
    private val audio: String,
    private val sourceUrl: String,
    private val licence: LicenseDTO
)
