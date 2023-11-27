package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.konkatenazia.tgmusicbotkt.model.SwearAccounting
import java.time.LocalDate

@Repository
interface SwearAccountingRepository : JpaRepository<SwearAccounting, Long> {
    fun findAllByCreatedEqualsAndIsGroupChatAndIsActive(created: LocalDate, isGroupChat: Boolean, isActive: Boolean) : List<SwearAccounting>
}