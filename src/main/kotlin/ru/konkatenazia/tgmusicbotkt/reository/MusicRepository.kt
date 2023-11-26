package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.jpa.repository.JpaRepository
import ru.konkatenazia.tgmusicbotkt.model.Music

interface MusicRepository : JpaRepository<Music, Long> {
    fun getFirstByAuthorAndGenre(author: String, genre: String) : Music
}