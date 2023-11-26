package ru.konkatenazia.tgmusicbotkt.services

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.User
import ru.konkatenazia.tgmusicbotkt.mapper.UserMapper
import ru.konkatenazia.tgmusicbotkt.model.ChatUser
import ru.konkatenazia.tgmusicbotkt.reository.ChatUserRepository

@Service
class UserService(
    val chatUserRepository: ChatUserRepository,
    val userMapper: UserMapper
) {
    fun isUserSaved(user: User): Boolean {
        val findUser = chatUserRepository.findById(user.id)
        return findUser.isPresent
    }

    fun saveUser(user: User): ChatUser {
        val chatUser = userMapper.toChatUser(user)
        return chatUserRepository.save(chatUser)
    }
}