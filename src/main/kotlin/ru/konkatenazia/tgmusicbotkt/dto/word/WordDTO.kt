package ru.konkatenazia.tgmusicbotkt.dto.word

data class WordDTO(
    private val word: String,
    private val phonetic: String,
    private val phonetics: List<PhoneticDTO>,
    private val meanings: List<MeaningDTO>,
    private val license: LicenseDTO,
    private val sourceUrls: List<String>
)
