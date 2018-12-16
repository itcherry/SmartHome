package com.smarthome.SmartHome.model

import com.pi4j.io.gpio.Pin
import com.pi4j.io.gpio.RaspiPin

enum class Pin(val pinId: Int, private val raspiPin: Pin) {
    GPIO_0(0, RaspiPin.GPIO_00),
    GPIO_01(1, RaspiPin.GPIO_01),
    GPIO_02(2, RaspiPin.GPIO_02),
    GPIO_03(3, RaspiPin.GPIO_03),
    GPIO_04(4, RaspiPin.GPIO_04),
    GPIO_05(5, RaspiPin.GPIO_05),
    GPIO_06(6, RaspiPin.GPIO_06),
    GPIO_07(7, RaspiPin.GPIO_07),
    GPIO_08(8, RaspiPin.GPIO_08),
    GPIO_09(9, RaspiPin.GPIO_09),
    GPIO_10(10, RaspiPin.GPIO_10),
    GPIO_11(11, RaspiPin.GPIO_11),
    GPIO_12(12, RaspiPin.GPIO_12),
    GPIO_13(13, RaspiPin.GPIO_13),
    GPIO_14(14, RaspiPin.GPIO_14),
    GPIO_15(15, RaspiPin.GPIO_15),
    GPIO_16(16, RaspiPin.GPIO_16),
    GPIO_17(17, RaspiPin.GPIO_17),
    GPIO_18(18, RaspiPin.GPIO_18),
    GPIO_19(19, RaspiPin.GPIO_19),
    GPIO_20(20, RaspiPin.GPIO_20),
    GPIO_21(21, RaspiPin.GPIO_21),
    GPIO_22(22, RaspiPin.GPIO_22),
    GPIO_23(23, RaspiPin.GPIO_23),
    GPIO_24(24, RaspiPin.GPIO_24),
    GPIO_25(25, RaspiPin.GPIO_25),
    GPIO_26(26, RaspiPin.GPIO_26),
    GPIO_27(27, RaspiPin.GPIO_27),
    GPIO_28(28, RaspiPin.GPIO_28),
    GPIO_29(29, RaspiPin.GPIO_29);

    companion object {
        fun getRaspiPinById(id: Int): Pin? {
            Pin.values().forEach {
                if (id == it.pinId) return it.raspiPin
            }
            return null
        }
    }
}