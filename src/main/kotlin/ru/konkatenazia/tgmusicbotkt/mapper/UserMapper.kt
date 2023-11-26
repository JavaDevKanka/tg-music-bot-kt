package ru.konkatenazia.tgmusicbotkt.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy
import org.telegram.telegrambots.meta.api.objects.User
import ru.konkatenazia.tgmusicbotkt.model.ChatUser

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    abstract fun toChatUser(user: User): ChatUser
}