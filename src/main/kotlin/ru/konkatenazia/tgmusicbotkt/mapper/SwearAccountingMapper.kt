package ru.konkatenazia.tgmusicbotkt.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.konkatenazia.tgmusicbotkt.model.ChatUser
import ru.konkatenazia.tgmusicbotkt.model.SwearAccounting

@Mapper(componentModel = "spring")
abstract class SwearAccountingMapper {


    @Mapping(target = "chatUser", source = "chatUser")
    @Mapping(target = "swearWord", source = "message")
    @Mapping(target = "chatId", source = "chatId")
    @Mapping(target = "isGroupChat", source = "isGroupChat")
    @Mapping(target = "isActive", source = "isActive")
    abstract fun toSwearAccounting(
        chatUser: ChatUser,
        message: String,
        chatId: Long,
        isGroupChat: Boolean,
        isActive: Boolean
    ): SwearAccounting

}