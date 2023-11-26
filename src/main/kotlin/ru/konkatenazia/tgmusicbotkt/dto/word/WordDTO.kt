package ru.konkatenazia.tgmusicbotkt.dto.word

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WordDTO(
    val word: String?,
    val phonetic: String?,
    val phonetics: List<PhoneticDTO>?,
    val meanings: List<MeaningDTO>?,
    val license: LicenseDTO?,
    val sourceUrls: List<String>?
)
