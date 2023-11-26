package ru.konkatenazia.tgmusicbotkt.model

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "user_table")
class ChatUser(
    var userName: String,
    var firstName: String,
    var lastName: String,

    @CreationTimestamp
    var created: LocalDateTime,

    @Id
    var id: Long,
)