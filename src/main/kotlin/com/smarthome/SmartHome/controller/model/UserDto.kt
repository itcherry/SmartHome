package com.smarthome.SmartHome.controller.model

import javax.validation.constraints.NotNull

data class UserDto(@field: NotNull val id: Long,
                   @field: NotNull val login: String,
                   @field: NotNull val password: String,
                   val securityToken: String? = null)