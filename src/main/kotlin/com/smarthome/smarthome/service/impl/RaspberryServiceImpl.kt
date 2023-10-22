package com.smarthome.smarthome.service.impl

import com.smarthome.smarthome.repository.RaspberryRepository
import com.smarthome.smarthome.repository.entity.Raspberry
import com.smarthome.smarthome.service.PinService
import com.smarthome.smarthome.service.RaspberryService
import com.smarthome.smarthome.service.impl.pin.model.SensorToPin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class RaspberryServiceImpl @Autowired constructor(
    private val pinService: PinService,
    private val raspberryRepository: RaspberryRepository
) : RaspberryService {

    /* Security */
    @Transactional
    override fun enableSecurity() {
        processSecurity(true)
    }

    @Transactional
    override fun disableSecurity() {
        processSecurity(false)
    }

    private fun processSecurity(doEnable: Boolean) {
        val optionalRaspberry = raspberryRepository.findById(1L)
        val newRaspberry = optionalRaspberry.orElse(Raspberry(1L, doEnable))
            .copy(isSecurityEnabled = doEnable)
        raspberryRepository.save(newRaspberry)
    }

    @Transactional(readOnly = true)
    override fun isSecurityEnabled(): Boolean {
        return raspberryRepository.findById(1L).orElse(Raspberry(1L)).isSecurityEnabled
    }

    /* Rozetka */
    @Transactional
    override fun setRozetkaState(place: SensorToPin, isEnabled: Boolean) {
        pinService.setMultipurposeSensor(place, isEnabled)
        storeToDb(place, isEnabled)
    }

    @Transactional
    override fun getRozetkaState(place: SensorToPin): Boolean = pinService.getMultipurposeSensor(place)

    /* Light */
    @Transactional
    override fun pulseLightState(place: SensorToPin) {
        val previousState = getLightState(place.getLightInputFromOutput())
        pinService.pulseMultipurposeSensor(place)
        storeToDb(place, !previousState)
    }

    @Transactional
    override fun getLightState(place: SensorToPin): Boolean = !pinService.getSensor(place)

    @Transactional
    override fun restoreRozetkasAndLights() {
        val raspberryState = raspberryRepository.findById(1L).orElse(Raspberry(1L))

        // Rozetkas

        setRozetkaState(SensorToPin.BEDROOM_ROZETKA_OUTPUT, raspberryState.isBedroomRozetkaEnabled)
        setRozetkaState(SensorToPin.LIVING_ROOM_ROZETKA_OUTPUT, raspberryState.isLivingRoomRozetkaEnabled)
        setRozetkaState(SensorToPin.AQUARIUM_OUTPUT, raspberryState.isAquariumRozetkaEnabled)

        // No need to store alarm state, since alarm listener is initialised after launch
        // setRozetkaState(SensorToPin.ALARM_OUTPUT, raspberryState.isAlarmEnabled)

        // Lights
        val isCorridorLightsEnabled = getLightState(SensorToPin.CORRIDOR_LIGHT_INPUT)
        val isBedroomLightsEnabled = getLightState(SensorToPin.BEDROOM_LIGHT_INPUT)
        val isKitchenLightsEnabled = getLightState(SensorToPin.KITCHEN_LIGHT_INPUT)
        val isLivingRoomLightsEnabled = getLightState(SensorToPin.LIVING_ROOM_LIGHT_INPUT)

        if(isCorridorLightsEnabled != raspberryState.isCorridorLightsEnabled) pulseLightState(SensorToPin.CORRIDOR_LIGHT_OUTPUT)
        if(isBedroomLightsEnabled != raspberryState.isBedroomLightsEnabled) pulseLightState(SensorToPin.BEDROOM_LIGHT_OUTPUT)
        if(isKitchenLightsEnabled != raspberryState.isKitchenLightsEnabled) pulseLightState(SensorToPin.KITCHEN_LIGHT_OUTPUT)
        if(isLivingRoomLightsEnabled != raspberryState.isLivingRoomLightsEnabled) pulseLightState(SensorToPin.LIVING_ROOM_LIGHT_OUTPUT)
    }

    private fun storeToDb(place: SensorToPin, isEnabled: Boolean) {
        val optionalRaspberry = raspberryRepository.findById(1L)

        val newRaspberry = optionalRaspberry.orElse(Raspberry(1L)).run {
            when (place) {
                SensorToPin.CORRIDOR_LIGHT_OUTPUT -> copy(id = 1L, isCorridorLightsEnabled = isEnabled)
                SensorToPin.KITCHEN_LIGHT_OUTPUT -> copy(id = 1L, isKitchenLightsEnabled = isEnabled)
                SensorToPin.LIVING_ROOM_LIGHT_OUTPUT -> copy(id = 1L, isLivingRoomLightsEnabled = isEnabled)
                SensorToPin.BEDROOM_LIGHT_OUTPUT -> copy(id = 1L, isBedroomLightsEnabled = isEnabled)
                SensorToPin.BEDROOM_ROZETKA_OUTPUT -> copy(id = 1L, isBedroomRozetkaEnabled = isEnabled)
                SensorToPin.LIVING_ROOM_ROZETKA_OUTPUT -> copy(id = 1L, isLivingRoomRozetkaEnabled = isEnabled)
                SensorToPin.AQUARIUM_OUTPUT -> copy(id = 1L, isAquariumRozetkaEnabled = isEnabled)
                SensorToPin.ALARM_OUTPUT -> copy(id = 1L, isAlarmEnabled = isEnabled)
                else -> optionalRaspberry.get()
            }
        }
        raspberryRepository.save(newRaspberry)
    }
}