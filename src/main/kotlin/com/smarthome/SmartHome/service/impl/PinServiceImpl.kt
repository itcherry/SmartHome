package com.smarthome.SmartHome.service.impl

import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioPinDigitalInput
import com.pi4j.io.gpio.GpioPinDigitalOutput
import com.smarthome.SmartHome.model.Pin
import com.smarthome.SmartHome.model.PinDirection
import com.smarthome.SmartHome.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class PinServiceImpl @Autowired constructor(
       // private val gpio: GpioController
) : PinService {
    override fun pulsePin(pinId: Int) {
        val raspiPin = Pin.getRaspiPinById(pinId)
        raspiPin?.let {
            /*val pin = if (gpio.getProvisionedPin(raspiPin) != null) {
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalOutput
            } else {
                gpio.provisionDigitalOutputPin(raspiPin)
            }

            pin.pulse(100, true)*/

            println("Pin $pinId pulse for 0.1 sec")
        }
    }

    override fun setPin(pinId: Int, isEnabled: Boolean) {
        val raspiPin = Pin.getRaspiPinById(pinId)
        raspiPin?.let {
            /*val pin = if (gpio.getProvisionedPin(raspiPin) != null) {
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalOutput
            } else {
                gpio.provisionDigitalOutputPin(raspiPin)
            }

            pin.setState(isEnabled)*/

            val enabledStr = if (isEnabled) "enabled" else "disabled"
            println("Set Pin $pinId = $enabledStr!")
        }
    }

    override fun getPin(pinId: Int): Boolean {
        val raspiPin = Pin.getRaspiPinById(pinId)
        raspiPin?.let {
           /* val pin = if (gpio.getProvisionedPin(raspiPin) != null) {
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalInput
            } else {
                gpio.provisionDigitalInputPin(raspiPin)
            }
            val pinState = pin.state
            println("Get Pin $pinId = ${pin.isHigh}!")

            return pin.isHigh*/
        }
        throw RuntimeException("Pin $raspiPin is null. Can't get pin state")
    }

    override fun setSensor(sensor: SensorToPin, isEnabled: Boolean) {
        if (sensor.direction == PinDirection.OUT) {
            setPin(sensor.pin.pinId, isEnabled)
        } else {
            throw RuntimeException("Pin direction should be OUTPUT!!")
        }
    }

    override fun getSensor(sensor: SensorToPin): Boolean {
        if (sensor.direction == PinDirection.IN) {
            return getPin(sensor.pin.pinId)
        } else {
            throw RuntimeException("Pin direction should be INPUT!!")
        }
    }

    override fun getOutPin(pinId: Int): Boolean {
        val raspiPin = Pin.getRaspiPinById(pinId)
        raspiPin?.let {
            /*val pin = if (gpio.getProvisionedPin(raspiPin) != null) {
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalOutput
            } else {
                gpio.provisionDigitalOutputPin(raspiPin)
            }
            val pinState = pin.state
            println("Get Pin $pinId = ${pin.isHigh}!")

            return pin.isHigh*/
        }
        throw RuntimeException("Pin $raspiPin is null. Can't get pin state")
    }

    override fun getOutSensor(sensor: SensorToPin): Boolean {
        if (sensor.direction == PinDirection.OUT) {
            return getPin(sensor.pin.pinId)
        } else {
            throw RuntimeException("Pin direction should be OUTPUT!!")
        }
    }
}