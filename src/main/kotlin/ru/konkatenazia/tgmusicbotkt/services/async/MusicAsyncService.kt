package ru.konkatenazia.tgmusicbotkt.services.async

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.konkatenazia.tgmusicbotkt.services.FileOperationService
import ru.konkatenazia.tgmusicbotkt.services.MusicService
import java.util.concurrent.CompletableFuture

@Service
class MusicAsyncService(
    val fileOperationService: FileOperationService,
    val musicService: MusicService
) {
    val logger: Logger = LoggerFactory.getLogger(MusicAsyncService::class.java)

    fun saveMusicAsync(musicArchives: Array<MultipartFile>) {
        val filePaths = ArrayList<String>()

        for (multipartFile in musicArchives) {
            val filePath = fileOperationService.saveArchiveAndGetAbsPath(multipartFile)
            filePaths.add(filePath)
        }

        val extractedFilesPath = fileOperationService.extractArchivesAndGetAbsPaths(filePaths)

        CompletableFuture.runAsync {
            logger.info("Сохранение музыки асинхронно завершено!")
            for (musicPath in extractedFilesPath) {
                musicService.saveMusicMetaData(musicPath)
            }
        }.thenRun {
            fileOperationService.removeUnpackedArchives(filePaths)
        }
    }
}