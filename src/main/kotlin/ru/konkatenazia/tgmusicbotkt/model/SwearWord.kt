package ru.konkatenazia.tgmusicbotkt.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "swear_word")
class SwearWord(
    var word: String,

    @Id
    var id: Long,
)

