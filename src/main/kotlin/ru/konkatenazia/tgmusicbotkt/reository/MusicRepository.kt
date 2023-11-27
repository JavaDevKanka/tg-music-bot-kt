package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.konkatenazia.tgmusicbotkt.model.Music

interface MusicRepository : JpaRepository<Music, Long> {
    fun getFirstByAuthorAndGenre(author: String, genre: String) : Music?

    @Query(value = "SELECT DISTINCT LEFT(author, 1) AS first_letter FROM music", nativeQuery = true)
    fun getUniqueAuthorFirstLetters(): List<String>
}