package ru.konkatenazia.tgmusicbotkt.model

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "song")
class Song(
    var songName: String,
    var album: String,
    var pathToFile: String,

    @CreationTimestamp
    var created: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id", referencedColumnName = "id", nullable = false)
    var music: Music,

    @Id
    var id: UUID? = null
)


