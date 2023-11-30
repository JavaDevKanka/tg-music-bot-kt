package ru.konkatenazia.tgmusicbotkt.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "swear_accounting")
class SwearAccounting(
    var swearWord: String,
    var chatId: Long,
    var isGroupChat: Boolean,
    var isActive: Boolean,

    @ManyToOne
    @JoinColumn(name = "user_table_id", referencedColumnName = "id", nullable = false)
    @Fetch(FetchMode.JOIN)
    var chatUser: ChatUser,

    @CreationTimestamp
    var created: LocalDate,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
)