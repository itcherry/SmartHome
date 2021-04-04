package com.smarthome.SmartHome.service.impl.temperature_humidity.model;

import com.pi4j.io.gpio.Pin;

@Deprecated
public interface DHTxx {
    void init() throws Exception;

    Pin getPin();

    void setPin(Pin pin);

    DhtData getData() throws Exception;
}
