package ru.konkatenazia.tgmusicbotkt.dto.word

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DefinitionDTO(
    val definition: String?,
    val synonyms: List<String>?,
    val antonyms: List<String>?,
    val example: String?
)
