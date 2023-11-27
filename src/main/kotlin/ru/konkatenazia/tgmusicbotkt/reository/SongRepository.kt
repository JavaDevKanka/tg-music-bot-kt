package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.konkatenazia.tgmusicbotkt.model.Song
import java.util.UUID

@Repository
interface SongRepository : JpaRepository<Song, UUID> {

    fun getSongBySongName(songName: String): Song?

    @Query(value = "select path_to_file from song order by random() limit 1", nativeQuery = true)
    fun getRandomSong(): String

    fun findAllBySongNameStartingWith(firstLetter: String, pageable: Pageable): List<Song>
    fun findAllBySongNameStartingWithAndIdGreaterThan(firstLetter: String, lastSongId: UUID, pageable: PageRequest): List<Song>


}