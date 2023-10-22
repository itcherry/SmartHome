package com.smarthome.smarthome.converter

import com.smarthome.smarthome.repository.entity.User
import com.smarthome.smarthome.controller.model.UserDto
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class UserToUserDtoConverter : Converter<User, UserDto> {
    override fun convert(user: User): UserDto {
        return UserDto(user.id, user.login, user.password)
    }
}