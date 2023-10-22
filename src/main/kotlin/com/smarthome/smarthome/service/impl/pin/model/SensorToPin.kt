package com.smarthome.smarthome.service.impl.pin.model

enum class SensorToPin(val pin: Pin, val direction: PinDirection) {
    CORRIDOR_LIGHT_OUTPUT(Pin.GPIO_14, PinDirection.MULTIPURPOSE),
    CORRIDOR_LIGHT_INPUT(Pin.GPIO_17, PinDirection.IN),
    KITCHEN_LIGHT_OUTPUT(Pin.GPIO_21, PinDirection.MULTIPURPOSE),
    KITCHEN_LIGHT_INPUT(Pin.GPIO_27, PinDirection.IN),
    LIVING_ROOM_LIGHT_OUTPUT(Pin.GPIO_15, PinDirection.MULTIPURPOSE),
    LIVING_ROOM_LIGHT_INPUT(Pin.GPIO_03, PinDirection.IN),
    BEDROOM_LIGHT_OUTPUT(Pin.GPIO_16, PinDirection.MULTIPURPOSE),
    BEDROOM_LIGHT_INPUT(Pin.GPIO_22, PinDirection.IN),
    BEDROOM_ROZETKA_OUTPUT(Pin.GPIO_25, PinDirection.MULTIPURPOSE),
    BOILER_OUTPUT(Pin.GPIO_23, PinDirection.MULTIPURPOSE),
    LIVING_ROOM_ROZETKA_OUTPUT(Pin.GPIO_12, PinDirection.MULTIPURPOSE),
    ALARM_OUTPUT(Pin.GPIO_07, PinDirection.MULTIPURPOSE),
    AQUARIUM_OUTPUT(Pin.GPIO_24, PinDirection.MULTIPURPOSE),
    NEPTUN_INPUT(Pin.GPIO_05, PinDirection.IN),
    SECURITY_INPUT(Pin.GPIO_11, PinDirection.IN),
    FIRE_INPUT(Pin.GPIO_09, PinDirection.IN);


    fun getLightInputFromOutput() = when(this){
        CORRIDOR_LIGHT_OUTPUT -> CORRIDOR_LIGHT_INPUT
        KITCHEN_LIGHT_OUTPUT -> KITCHEN_LIGHT_INPUT
        LIVING_ROOM_LIGHT_OUTPUT -> LIVING_ROOM_LIGHT_INPUT
        BEDROOM_LIGHT_OUTPUT -> BEDROOM_LIGHT_INPUT
        else -> this
    }

}