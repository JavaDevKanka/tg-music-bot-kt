package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.konkatenazia.tgmusicbotkt.model.Music
import ru.konkatenazia.tgmusicbotkt.model.Song
import java.util.*

@Repository
interface MusicRepository : JpaRepository<Music, Long> {
    fun getFirstByAuthorAndGenre(author: String, genre: String) : Music?

    @Query(value = "SELECT DISTINCT LEFT(author, 1) AS first_letter FROM music", nativeQuery = true)
    fun getUniqueAuthorFirstLetters(): List<String>

    fun findAllByAuthorStartingWith(firstLetter: String, pageable: Pageable): List<Music>
    fun findAllByAuthorStartingWithAndIdGreaterThan(firstLetter: String, lastSongId: Long, pageable: PageRequest): List<Music>
}