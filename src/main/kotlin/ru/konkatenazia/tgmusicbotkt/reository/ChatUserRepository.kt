package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.konkatenazia.tgmusicbotkt.model.ChatUser

@Repository
interface ChatUserRepository : JpaRepository<ChatUser, Long> {
}