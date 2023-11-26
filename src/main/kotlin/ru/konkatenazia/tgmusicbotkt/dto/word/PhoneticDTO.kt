package ru.konkatenazia.tgmusicbotkt.dto.word

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PhoneticDTO(
    val text: String?,
    val audio: String?,
    val sourceUrl: String?,
    val licence: LicenseDTO?
)
