package ru.konkatenazia.tgmusicbotkt.services

import org.apache.commons.compress.archivers.sevenz.SevenZFile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

@Service
class FileOperationService {

    @Value("\${file.archive.path}")
    val MUSIC_ARCHIVE_PATH: String? = null

    @Value("\${file.music.path}")
    val UNPACKED_MUSIC_PATH: String? = null

    val logger: Logger = LoggerFactory.getLogger(FileOperationService::class.java)

    fun removeUnpackedArchives(pathsToArchive: List<String>) {
        pathsToArchive.forEach(this::removeUnpackedArchive)
    }

    fun removeUnpackedArchive(pathToArchive: String) {
        val file = File(pathToArchive)
        val fileName = file.name
        if (file.exists()) {
            val deleted: Boolean = file.delete()
            if (deleted) {
                logger.info("Архив $fileName был удален после распаковки!")
            } else {
                logger.info("Архив $fileName удалить не удалось!")
            }
        } else {
            logger.info("Файл $fileName не существует!")
        }
    }

    fun saveArchiveAndGetAbsPath(musicArchive: MultipartFile): String {
        try {
            val bytes = musicArchive.bytes

            val musicArchiveDir = File(MUSIC_ARCHIVE_PATH!!)
            if (!musicArchiveDir.exists()) {
                val created = musicArchiveDir.mkdirs()
                require(created) { "Не удалось создать директорию для архивов музыки" }
            }
            val outputFile = File(MUSIC_ARCHIVE_PATH, musicArchive.originalFilename!!)
            if (!outputFile.exists()) {
                val stream = BufferedOutputStream(FileOutputStream(outputFile))
                stream.write(bytes)
                stream.close()
                return outputFile.absolutePath
            }
            logger.warn("Файл с именем ${outputFile.name} уже сохранен!")
            throw RuntimeException()
        } catch (e: Exception) {
            logger.error(e.message)
            throw RuntimeException("Не удалось сохранить файл ${musicArchive.name}")
        }
    }

    fun extractArchivesAndGetAbsPaths(pathsToArchive: List<String>): List<String> {
        return pathsToArchive.flatMap { extractArchiveAndGetAbsPath(it) }
    }

    fun extractArchiveAndGetAbsPath(archiveName: String): List<String> {
        val unpackedSongsAbsPaths = ArrayList<String>()
        if (!archiveName.endsWith(".7z")) {
            logger.info("Файл с именем \" $archiveName \" не является архивом .7z")
        }
        val sevenZFile: SevenZFile = SevenZFile(File(archiveName))
        sevenZFile.use {
            val entry = sevenZFile.nextEntry
            while (entry != null) {
                if (entry.isDirectory) {
                    continue
                }
                val entryName = entry.name
                if (entryName.endsWith(".flac") || entryName.endsWith(".mp3")) {
                    logger.info("Распаковывается файл \" $entryName \"")

                    val unpackedMusicDir = File(UNPACKED_MUSIC_PATH!!)
                    if (!unpackedMusicDir.exists()) {
                        val created = unpackedMusicDir.mkdirs()
                        if (!created) {
                            throw RuntimeException("Не удалось создать директорию $unpackedMusicDir для распаковываемой музыки")
                        }
                    }
                    val outputFile = File(UNPACKED_MUSIC_PATH, entryName)
                    val parentDir = outputFile.parentFile
                    if (!parentDir.exists()) {
                        val parentDirMk = parentDir.mkdirs()
                    }
                    FileOutputStream(outputFile).use {
                        val buffer = ByteArray(1024)
                        var length: Int
                        while (sevenZFile.read(buffer).also { length = it } != -1) {
                            it.write(buffer, 0, length)
                        }
                    }
                    unpackedSongsAbsPaths.add(outputFile.absolutePath)
                }
            }
        }
        return unpackedSongsAbsPaths
    }
}