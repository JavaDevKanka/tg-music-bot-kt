package ru.konkatenazia.tgmusicbotkt.services

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.konkatenazia.tgmusicbotkt.reository.SwearAccountingRepository
import ru.konkatenazia.tgmusicbotkt.services.basebot.BotHeart
import java.time.LocalDate

@Service
class ScheduleTaskService(
    val botHeart: BotHeart,
    val swearAccountingRepository: SwearAccountingRepository
) {

    val logger: Logger = LoggerFactory.getLogger(ScheduleTaskService::class.java)

    @Scheduled(cron = "\${cron.task.send.swear.result}", zone = "Europe/Moscow")
    @SchedulerLock(name = "SEND_SWEAR_RESULT", lockAtLeastFor = "30S", lockAtMostFor = "60S")
    fun sendSwearResults() {
        val resultToSend = HashMap<Long, StringBuilder>()
        val stringBuilder = StringBuilder()
        stringBuilder.append("Доска почета сапожников дня: \n")

        val swears = swearAccountingRepository.findAllByCreatedEqualsAndIsGroupChatAndIsActive(
            LocalDate.now(),
            isGroupChat = true,
            isActive = true
        )
        val users = swears.stream()
            .map { it.chatUser }
            .distinct()
            .toList()

        for (chatUser in users) {
            stringBuilder.append("@").append(chatUser.userName)
                .append(" ")
                .append("(")
                .append(chatUser.firstName)
                .append(")")
                .append("\n")
            var count = 1
            for (swearAccounting in swears) {
                if (swearAccounting.chatUser == chatUser
                    && swearAccounting.created == LocalDate.now()) {
                    stringBuilder
                        .append(count)
                        .append(") ")
                        .append(swearAccounting.swearWord)
                        .append(" ;\n")
                    count++
                    resultToSend[swearAccounting.chatId] = stringBuilder
                }
            }
        }
        resultToSend.forEach {
            logger.info("Результат из scheduler отправляется в чат: ${it.key}")
            botHeart.sendMessage(it.key, it.value.toString())

        }
        swears.forEach {
            it.isActive = false
        }
        swearAccountingRepository.saveAll(swears)
    }
}