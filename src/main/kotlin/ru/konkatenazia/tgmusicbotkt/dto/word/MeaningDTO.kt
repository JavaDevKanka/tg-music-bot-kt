package ru.konkatenazia.tgmusicbotkt.dto.word

data class MeaningDTO(
    private val partOfSpeech: String,
    private val definitions: List<DefinitionDTO>,
    private val synonyms: List<String>,
    private val antonyms: List<String>,
    private val example: String
)
