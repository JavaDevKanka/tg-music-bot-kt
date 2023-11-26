package ru.konkatenazia.tgmusicbotkt.dto.word

data class WordDTO(
    val word: String,
    val phonetic: String,
    val phonetics: List<PhoneticDTO>,
    val meanings: List<MeaningDTO>,
    val license: LicenseDTO,
    val sourceUrls: List<String>
)
