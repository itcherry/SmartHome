package com.smarthome.SmartHome.service.impl

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.smarthome.SmartHome.model.Pins
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PinServiceImpl @Autowired constructor(
        private val gpio: GpioController
) : PinService {
    override fun pulsePin(pinId: Int) {
        val raspiPin = Pins.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = if(gpio.getProvisionedPin(raspiPin) != null){
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalOutput
            } else {
                gpio.provisionDigitalOutputPin(raspiPin)
            }

            pin.pulse(100, true)

            println("Pin $pinId pulse for 0.1 sec")
        }
    }

    override fun setPin(pinId: Int, isEnabled: Boolean) {
        val raspiPin = Pins.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = if(gpio.getProvisionedPin(raspiPin) != null){
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalOutput
            } else {
                gpio.provisionDigitalOutputPin(raspiPin)
            }

            pin.setState(isEnabled)

            val enabledStr = if (isEnabled) "enabled" else "disabled"
            println("Pin $pinId $enabledStr!")
        }
    }

    override fun getPin(pinId: Int): String {
        val raspiPin = Pins.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = if(gpio.getProvisionedPin(raspiPin) != null){
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalOutput
            } else {
                gpio.provisionDigitalOutputPin(raspiPin)
            }
            val pinState = pin.state
            val isPinHigh = pin.isHigh

            return "Pin $pinId Is high: ${!isPinHigh}!"
        }
        return "RaspiPin is null :("
    }
}