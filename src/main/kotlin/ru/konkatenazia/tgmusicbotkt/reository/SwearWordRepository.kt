package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.konkatenazia.tgmusicbotkt.model.SwearWord

@Repository
interface SwearWordRepository : JpaRepository<SwearWord, Long> {
    fun existsByWordInIgnoreCase(word: List<String>): Boolean
}