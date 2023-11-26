package ru.konkatenazia.tgmusicbotkt.dto.word

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class LicenseDTO(
    val name: String?,
    val url: String?
)
