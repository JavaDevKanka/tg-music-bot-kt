package ru.konkatenazia.tgmusicbotkt.dto.word

data class DefinitionDTO(
    val definition: String?,
    val synonyms: List<String>?,
    val antonyms: List<String>?,
    val example: String?
)
