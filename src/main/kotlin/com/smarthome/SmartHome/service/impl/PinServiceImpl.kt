package com.smarthome.SmartHome.service.impl

import com.pi4j.io.gpio.*
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.smarthome.SmartHome.model.Pins


@Service
class PinServiceImpl @Autowired constructor(
        private val gpio: GpioController
) : PinService {
    override fun pulsePin(pinId: Int) {
        val raspiPin = Pins.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = gpio.provisionDigitalOutputPin(raspiPin)
            pin.pulse(100, true)

            gpio.unprovisionPin(pin)

            println("Pin $pinId pulse for 0.1 sec")
        }
    }

    override fun setPin(pinId: Int, isEnabled: Boolean) {
        val raspiPin = Pins.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = gpio.provisionDigitalOutputPin(raspiPin)
            pin.setState(isEnabled)

            gpio.unprovisionPin(pin)

            val enabledStr = if (isEnabled) "enabled" else "disabled"
            println("Pin $pinId $enabledStr!")
        }
    }

    override fun getPin(pinId: Int): String {
        val raspiPin = Pins.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = gpio.provisionDigitalInputPin(raspiPin, PinPullResistance.PULL_DOWN)

            val pinState = pin.state
            val isPinHigh = pin.isHigh

            gpio.unprovisionPin(pin)

            return "Pin $pinId Is high: ${!isPinHigh}!"
        }
        return "RaspiPin is null :("
    }
}