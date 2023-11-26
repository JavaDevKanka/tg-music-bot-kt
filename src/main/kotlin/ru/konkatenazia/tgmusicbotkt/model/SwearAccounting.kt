package ru.konkatenazia.tgmusicbotkt.model

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "swear_accounting")
class SwearAccounting(
    var swearWord: String,
    var chatId: Long,
    var isGroupChat: Boolean,
    var isActive: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_table_id", referencedColumnName = "id", nullable = false)
    var chatUser: ChatUser,

    @CreationTimestamp
    var created: LocalDate,

    @Id
    var id: Long,
)