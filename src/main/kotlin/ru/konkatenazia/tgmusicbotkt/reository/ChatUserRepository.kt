package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.jpa.repository.JpaRepository
import ru.konkatenazia.tgmusicbotkt.model.ChatUser

interface ChatUserRepository : JpaRepository<ChatUser, Long> {
}