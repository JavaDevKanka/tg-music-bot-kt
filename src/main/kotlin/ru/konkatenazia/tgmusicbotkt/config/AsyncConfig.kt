package ru.konkatenazia.tgmusicbotkt.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@Configuration
@EnableAsync
data class AsyncConfig(
    @Value("\${async.pool.corePoolSize}")
    val corePoolSize: Int,
    @Value("\${async.pool.maxPoolSize}")
    val maxPoolSize: Int,
    @Value("\${async.pool.aliveTimeout}")
    val aliveTimeout: Long,
    @Value("\${async.pool.queueSize}")
    val queueSize: Int
) {
    @Bean
    fun executeSaveMusicAsync(): ExecutorService {
        return ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            aliveTimeout,
            TimeUnit.SECONDS,
            ArrayBlockingQueue(queueSize)
        )
    }
}