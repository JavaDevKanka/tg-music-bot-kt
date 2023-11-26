package ru.konkatenazia.tgmusicbotkt.dto.word

import com.fasterxml.jackson.annotation.JsonInclude

data class MeaningDTO(
    val partOfSpeech: String?,
    val definitions: List<DefinitionDTO>?,
    val synonyms: List<String>?,
    val antonyms: List<String>?,
    val example: String?
)
