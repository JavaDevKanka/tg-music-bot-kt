package ru.konkatenazia.tgmusicbotkt.dto.word

data class DefinitionDTO(
    private val definition: String,
    private val synonyms: List<String>,
    private val antonyms: List<String>,
    private val example: String
)
