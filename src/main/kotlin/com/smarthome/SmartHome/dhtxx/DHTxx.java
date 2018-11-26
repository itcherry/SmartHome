package com.smarthome.SmartHome.dhtxx;

import com.pi4j.io.gpio.Pin;

public interface DHTxx {
    void init() throws Exception;

    Pin getPin();

    void setPin(Pin pin);

    DhtData getData() throws Exception;
}
