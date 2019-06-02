package com.smarthome.SmartHome.converter

import com.smarthome.SmartHome.repository.entity.User
import com.smarthome.SmartHome.controller.model.UserDto
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class UserToUserDtoConverter : Converter<User, UserDto> {
    override fun convert(user: User): UserDto {
        return UserDto(user.id, user.login, user.password)
    }
}