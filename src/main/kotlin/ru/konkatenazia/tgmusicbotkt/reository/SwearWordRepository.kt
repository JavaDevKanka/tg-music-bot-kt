package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.jpa.repository.JpaRepository
import ru.konkatenazia.tgmusicbotkt.model.SwearWord

interface SwearWordRepository : JpaRepository<SwearWord, Long> {
    fun existsByWordInIgnoreCase(word: List<String>): Boolean
}