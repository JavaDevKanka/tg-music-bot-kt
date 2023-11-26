package ru.konkatenazia.tgmusicbotkt.services

import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.api.objects.InputFile
import ru.konkatenazia.tgmusicbotkt.model.Music
import ru.konkatenazia.tgmusicbotkt.model.Song
import ru.konkatenazia.tgmusicbotkt.reository.MusicRepository
import ru.konkatenazia.tgmusicbotkt.reository.SongRepository
import java.io.File

@Service
class MusicService(
    val songRepository: SongRepository,
    val musicRepository: MusicRepository
) {
    val logger: Logger = LoggerFactory.getLogger(MusicService::class.java)

    fun getRandomMusic(): InputFile {
        val randomSongPath = songRepository.getRandomSong();
        val file = InputFile()
        file.setMedia(File(randomSongPath))
        return file
    }

    @Transactional
    fun saveMusicMetaData(musicPath: String) {
        try {
            val audioFile = AudioFileIO.read(File(musicPath))
            val tag = audioFile.tag
            val author = tag.getFirst(FieldKey.ARTIST)
            val genre = tag.getFirst(FieldKey.GENRE)
            val album = tag.getFirst(FieldKey.ALBUM)
            val songName = tag.getFirst(FieldKey.TITLE)
            val songFromDb = songRepository.getSongBySongName(songName)
            if (songFromDb == null) {
                val musicFromDb = musicRepository.getFirstByAuthorAndGenre(author, genre)
                if (musicFromDb == null) {
                    val savedMusic = musicRepository.save(
                        Music(
                            genre = genre,
                            author = author
                        )
                    )
                    songRepository.save(
                        Song(
                            songName = songName,
                            album = album,
                            pathToFile = musicPath,
                            music = savedMusic
                        )
                    )
                } else {
                    songRepository.save(
                        Song(
                            songName = songName,
                            album = album,
                            pathToFile = musicPath,
                            music = musicFromDb
                        )
                    )
                }

            }
        } catch (e: Exception) {
            logger.info(e.printStackTrace().toString())
        }
    }
}