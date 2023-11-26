package ru.konkatenazia.tgmusicbotkt.services

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import ru.konkatenazia.tgmusicbotkt.dto.word.WordDTO
import ru.konkatenazia.tgmusicbotkt.reository.SwearWordRepository
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.regex.Pattern

@Service
class MessageProcessingService(
    val swearWordRepository: SwearWordRepository
) {
    val logger = LoggerFactory.getLogger(MessageProcessingService::class.java)
    fun checkForBadWords(messageText: String): Boolean {
        val words = messageText.lowercase().split("[,\\s]+")
        logger.info("Плохое слово в предложении -  \"$words\"")
        return swearWordRepository.existsByWordInIgnoreCase(words)
    }

    fun isEnglishLayout(messageText: String): Boolean {
        val regex = Regex("[a-zA-Z\\s\\p{Punct}]+")
        logger.info("Английская ли раскладка $messageText - ${messageText.matches(regex)}")
        return messageText.matches(regex)
    }

    fun isEnglishWord(wordForCheck: String): Boolean {
        val restTemplate = RestTemplate()
        val url = "https://api.dictionaryapi.dev/api/v2/entries/en/"

        val pattern = Pattern.compile("\\b\\w+\\b")
        val matcher = pattern.matcher(wordForCheck)
        val splittedWords = ArrayList<String>()
        while (matcher.find()) {
            val word = matcher.group()
            splittedWords.add(word)
        }

        for (word in splittedWords) {
            try {
                val response: ResponseEntity<Array<WordDTO>> =
                    restTemplate.getForEntity(url + word, Array<WordDTO>::class.java)
                val insultWords = response.body

                if (insultWords != null && response.statusCode.is2xxSuccessful) {
                    val count = insultWords
                        .filter { elem -> elem.meanings?.isNotEmpty() == true }
                        .filter { elem -> elem.meanings?.get(0)?.definitions?.isNotEmpty() == true }
                        .map { elem -> elem.meanings?.get(0)?.definitions?.get(0)?.definition }
                        .distinct()
                        .count()
                    logger.info("Количество строк: $count")
                    return count > 2
                }
            } catch (ex: HttpClientErrorException.NotFound) {
                logger.info("Слово: \"$word\" не найдено")
            }
        }
        return false
    }

    fun invertKeyboardLayout(text: String): String {
        val url = "https://raskladki.net.ru/post.php"
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        val params = "text=$text&lang=eng2rus"
        con.doOutput = true
        val os = con.outputStream
        os.write(params.toByteArray())
        os.flush()
        os.close()
        val `in` = BufferedReader(InputStreamReader(con.inputStream))
        var inputLine: String?
        val response = StringBuilder()
        while (`in`.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        `in`.close()
        return response.toString()
    }
}