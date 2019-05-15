package com.smarthome.SmartHome.dhtxx;

import com.pi4j.io.gpio.Pin;
import com.pi4j.wiringpi.Gpio;

public abstract class DHTxxBase implements DHTxx {
  private static final int DHT_MAXCOUNT = 32000;
  private static final int DHT_PULSES = 41;

  private Pin pin;

  public DHTxxBase(Pin pin) {
    this.pin = pin;
  }

  public Pin getPin() {
    return pin;
  }

  public void setPin(Pin pin) {
    this.pin = pin;
  }

  @Override
  public void init() throws Exception {
    /*
     * Initialize GPIO library.
     */
    //if (Gpio.wiringPiSetup() == -1) {
     // throw new Exception("DHT_ERROR_GPIO");
   // }
  }

}
