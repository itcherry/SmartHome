package com.smarthome.SmartHome.error

enum class ZetProError(private val errorCode: Int, private val errorMessage: String) : ApiError {
    UNAUTHORIZED(401, ErrorMessages.ZET_PRO_UNAUTHORISED), NOT_SUCCESFUL(153, ErrorMessages.ZET_PRO_NOT_SUCCESSFULL);

    override fun getErrorCode(): Int {
        return errorCode
    }

    override fun getErrorMessage(): String {
        return errorMessage
    }

}