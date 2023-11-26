package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.jpa.repository.JpaRepository
import ru.konkatenazia.tgmusicbotkt.model.SwearAccounting
import java.time.LocalDate

interface SwearAccountingRepository : JpaRepository<SwearAccounting, Long> {
    fun findAllByCreatedEqualsAndIsGroupChatAndIsActive(created: LocalDate, isGroupChat: Boolean, isActive: Boolean) : List<SwearAccounting>
}