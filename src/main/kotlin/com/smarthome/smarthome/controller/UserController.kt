package com.smarthome.smarthome.controller

import com.smarthome.smarthome.security.JwtTokenProvider
import com.smarthome.smarthome.service.FcmTokenService
import com.smarthome.smarthome.service.UserService
import com.smarthome.smarthome.controller.model.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import com.smarthome.smarthome.controller.model.ResponseBody
import com.smarthome.smarthome.controller.model.ResponseBody.SUCCESS

@RestController
@RequestMapping(USER_VALUE)
class UserController @Autowired constructor(
        private val userService: UserService,
        private val fcmTokenService: FcmTokenService,
        private val conversionService: ConversionService,
        private val authenticationManager: AuthenticationManager,
        private val tokenProvider: JwtTokenProvider
) {
    @RequestMapping(method = [RequestMethod.POST], consumes = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun authenticateUser(@RequestBody userDto: UserDto): ResponseBody<UserDto> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        userDto.login,
                        userDto.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val user = userService.getUserByLogin(userDto.login)
        val resultUserDto: UserDto = conversionService.convert(user, UserDto::class.java)!!
        val jwt = tokenProvider.generateToken(authentication)

        return ResponseBody(
            SUCCESS,
            null,
            resultUserDto.copy(password = "", securityToken = jwt)
        )
    }

    @RequestMapping(method = [RequestMethod.PUT], value = ["/token/{fcmToken}"])
    @ResponseStatus(HttpStatus.OK)
    fun setUserToken(@PathVariable("fcmToken") fcmToken: String): ResponseBody<Any?> {
        fcmTokenService.addToken(fcmToken)
        return ResponseBody(SUCCESS, null, null)
    }
}