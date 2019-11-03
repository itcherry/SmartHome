package com.smarthome.SmartHome.service.impl.pin

import com.pi4j.io.gpio.*
import com.smarthome.SmartHome.service.impl.pin.model.Pin
import com.smarthome.SmartHome.service.impl.pin.model.PinDirection
import com.smarthome.SmartHome.service.impl.pin.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.pi4j.io.gpio.event.GpioPinListenerDigital
import com.pi4j.io.gpio.GpioPinDigitalInput
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent


@Service
class PinServiceImpl @Autowired constructor(
        private val gpio: GpioController
) : PinService {
    override fun setSecurityAlarmListener(listener: (GpioPinDigitalStateChangeEvent) -> Unit) {
        val securityRaspiPin = Pin.getRaspiPinById(SensorToPin.SECURITY_INPUT.pin.pinId)
        val pin = if (gpio.getProvisionedPin(securityRaspiPin) != null) {
            gpio.getProvisionedPin(securityRaspiPin) as GpioPinDigitalInput
        } else {
            gpio.provisionDigitalInputPin(securityRaspiPin)
        }

        pin.apply {
            setShutdownOptions(true)
            removeAllListeners()
            addListener(GpioPinListenerDigital { event ->
                if(event.state == PinState.LOW) {
                    listener.invoke(event)
                }
            })
        }
    }

    override fun setNeptunAlarmListener(listener: (GpioPinDigitalStateChangeEvent) -> Unit) {
        val neptunRaspiPin = Pin.getRaspiPinById(SensorToPin.NEPTUN_INPUT.pin.pinId)
        val pin = if (gpio.getProvisionedPin(neptunRaspiPin) != null) {
            gpio.getProvisionedPin(neptunRaspiPin) as GpioPinDigitalInput
        } else {
            gpio.provisionDigitalInputPin(neptunRaspiPin)
        }

        pin.apply {
            setShutdownOptions(true)
            removeAllListeners()
            addListener(GpioPinListenerDigital { event ->
                when (event.state) {
                    PinState.HIGH -> println("There is water at home")
                    else -> println("Water reduced. Congratulations plumber!")
                }
                if(event.state == PinState.LOW) {
                    listener.invoke(event)
                }
            })
        }
    }

    override fun setFireAlarmListener(listener: (GpioPinDigitalStateChangeEvent) -> Unit) {
        val fireRaspiPin = Pin.getRaspiPinById(SensorToPin.FIRE_INPUT.pin.pinId)
        val pin = if (gpio.getProvisionedPin(fireRaspiPin) != null) {
            gpio.getProvisionedPin(fireRaspiPin) as GpioPinDigitalInput
        } else {
            gpio.provisionDigitalInputPin(fireRaspiPin)
        }

        pin.apply {
            setShutdownOptions(true)
            removeAllListeners()
            addListener(GpioPinListenerDigital { event ->
                when (event.state) {
                    PinState.HIGH -> println("There is fire at home")
                    else -> println("Fire at home stopped. Congratulations firemen!")
                }
                if(event.state == PinState.LOW) {
                    listener.invoke(event)
                }
            })
        }
    }

    override fun setMultipurposePin(pinId: Int, isEnabled: Boolean) {
        val raspiPin = Pin.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = if (gpio.getProvisionedPin(raspiPin) != null) {
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalOutput
            } else {
                gpio.provisionDigitalOutputPin(raspiPin)
            }
            pin.setState(isEnabled)
            val enabledStr = if (isEnabled) "enabled" else "disabled"
            println("Set Pin $pinId = $enabledStr!")
        }
    }

    override fun pulseMultipurposePin(pinId: Int) {
        val raspiPin = Pin.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = if (gpio.getProvisionedPin(raspiPin) != null) {
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalMultipurpose
            } else {
                gpio.provisionDigitalMultipurposePin(raspiPin, PinMode.DIGITAL_OUTPUT)
            }
            pin.mode = PinMode.DIGITAL_OUTPUT
            pin.pulse(100, true)

            println("Pin $pinId pulse for 0.1 sec")
        }
    }

    override fun getMultipurposePin(pinId: Int): Boolean {
        val raspiPin = Pin.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = if (gpio.getProvisionedPin(raspiPin) != null) {
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalOutput
            } else {
                gpio.provisionDigitalOutputPin(raspiPin)
            }

            println("Get Pin $pinId = ${pin.isHigh}!")

            return pin.isHigh
        }
        throw RuntimeException("Pin $raspiPin is null. Can't get pin state")
    }

    override fun getMultipurposeSensor(sensor: SensorToPin): Boolean {
        if (sensor.direction == PinDirection.MULTIPURPOSE) {
            return getMultipurposePin(sensor.pin.pinId)
        } else {
            throw RuntimeException("Pin direction should be MULTIPURPOSE!!")
        }
    }

    override fun setMultipurposeSensor(sensor: SensorToPin, isEnabled: Boolean) {
        if (sensor.direction == PinDirection.MULTIPURPOSE) {
            setMultipurposePin(sensor.pin.pinId, isEnabled)
        } else {
            throw RuntimeException("Pin direction should be MULTIPURPOSE!!")
        }
    }

    override fun pulseMultipurposeSensor(sensor: SensorToPin) {
        if (sensor.direction == PinDirection.MULTIPURPOSE) {
            pulseMultipurposePin(sensor.pin.pinId)
        } else {
            throw RuntimeException("Pin direction should be MULTIPURPOSE!!")
        }
    }

    override fun setSensor(sensor: SensorToPin, isEnabled: Boolean) {
        if (sensor.direction == PinDirection.OUT) {
            setPin(sensor.pin.pinId, isEnabled)
        } else {
            throw RuntimeException("Pin direction should be OUTPUT!!")
        }
    }

    private fun setPin(pinId: Int, isEnabled: Boolean) {
        val raspiPin = Pin.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = if (gpio.getProvisionedPin(raspiPin) != null) {
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalOutput
            } else {
                gpio.provisionDigitalOutputPin(raspiPin)
            }
            pin.setState(isEnabled)

            val enabledStr = if (isEnabled) "enabled" else "disabled"
            println("Set Pin $pinId = $enabledStr!")
        }
    }

    override fun getSensor(sensor: SensorToPin): Boolean {
        if (sensor.direction == PinDirection.IN) {
            return getPin(sensor.pin.pinId)
        } else {
            throw RuntimeException("Pin direction should be INPUT!!")
        }
    }

    private fun getPin(pinId: Int): Boolean {
        val raspiPin = Pin.getRaspiPinById(pinId)
        raspiPin?.let {
            val pin = if (gpio.getProvisionedPin(raspiPin) != null) {
                gpio.getProvisionedPin(raspiPin) as GpioPinDigitalInput
            } else {
                gpio.provisionDigitalInputPin(raspiPin)
            }
            println("Get Pin $pinId = ${pin.isHigh}!")

            return pin.isHigh
        }
        throw RuntimeException("Pin $raspiPin is null. Can't get pin state")
    }
}