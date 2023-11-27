package ru.konkatenazia.tgmusicbotkt.reository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.konkatenazia.tgmusicbotkt.model.Song
import java.util.UUID

interface SongRepository : JpaRepository<Song, UUID> {

    fun getSongBySongName(songName: String): Song?

    @Query(value = "select path_to_file from song order by random() limit 1", nativeQuery = true)
    fun getRandomSong(): String
}