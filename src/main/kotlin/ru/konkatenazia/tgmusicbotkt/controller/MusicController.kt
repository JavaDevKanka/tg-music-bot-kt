package ru.konkatenazia.tgmusicbotkt.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.konkatenazia.tgmusicbotkt.services.async.MusicAsyncService

@RestController("/music")
data class MusicController(val musicAsyncService: MusicAsyncService) {

    @PostMapping(value = arrayOf("/uploadMusic"), consumes = [MULTIPART_FORM_DATA_VALUE])
    fun uploadMusic(@RequestPart("musicArchive") musicArchive: Array<MultipartFile>): ResponseEntity<Array<String>> {
        if (musicArchive.isNotEmpty()) {
            musicAsyncService.saveMusicAsync(musicArchive)
            return ResponseEntity(HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

}