package com.smarthome.SmartHome.model

enum class SensorToPin(val pin: Pin, val direction: PinDirection) {
    CORRIDOR_LIGHT_OUTPUT(Pin.GPIO_14, PinDirection.OUT),
    CORRIDOR_LIGHT_INPUT(Pin.GPIO_17, PinDirection.IN),
    KITCHEN_LIGHT_OUTPUT(Pin.GPIO_15, PinDirection.OUT),
    KITCHEN_LIGHT_INPUT(Pin.GPIO_27, PinDirection.IN),
    LIVING_ROOM_LIGHT_OUTPUT(Pin.GPIO_21, PinDirection.OUT),
    LIVING_ROOM_LIGHT_INPUT(Pin.GPIO_03, PinDirection.IN),
    BEDROOM_LIGHT_OUTPUT(Pin.GPIO_16, PinDirection.OUT),
    BEDROOM_LIGHT_INPUT(Pin.GPIO_22, PinDirection.IN),
    BEDROOM_ROZETKA_OUTPUT(Pin.GPIO_25, PinDirection.OUT),
    BOILER_OUTPUT(Pin.GPIO_23, PinDirection.OUT),
    LIVING_ROOM_ROZETKA_OUTPUT(Pin.GPIO_24, PinDirection.OUT),
    ALARM_OUTPUT(Pin.GPIO_07, PinDirection.OUT),
    DOOR_OUTPUT(Pin.GPIO_12, PinDirection.OUT),
    NEPTUN_INPUT(Pin.GPIO_05, PinDirection.IN)
}