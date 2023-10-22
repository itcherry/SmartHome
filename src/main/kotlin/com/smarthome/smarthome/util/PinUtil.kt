package com.smarthome.smarthome.util

import com.pi4j.io.gpio.PinState

fun PinState.stateBasedOnBoolean(isEnabled:Boolean)
    = if(isEnabled) PinState.HIGH else PinState.LOW