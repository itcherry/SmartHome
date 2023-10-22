package com.smarthome.smarthome.service.impl.temperature_humidity.model;

import com.pi4j.io.gpio.Pin;
import com.pi4j.wiringpi.Gpio;
import com.smarthome.smarthome.exception.ExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class DHT22 extends DHTxxBase {
  private static final Logger LOG = LoggerFactory.getLogger(ExceptionFactory.class);

  private static final int DHT_WAIT_INTERVAL = 2000;

  public DHT22(Pin pin) {
    super(pin);
  }

  @Override
  public DhtData getData() throws Exception {
    int atempts = 0;
    while (true) {
      try {
        int[] data = getRawData();


         //* Verify checksum of received data.

        if (data[4] != ((data[0] + data[1] + data[2] + data[3]) & 0xFF)) {
          throw new Exception("DHT_ERROR_CHECKSUM");
        }

         //* Calculate humidity and temp for DHT22 sensor.

        double humidity = (data[0] * 256 + data[1]) / 10.0;
        double temperature = ((data[2] & 0x7F) * 256 + data[3]) / 10.0;
        if ((data[2] & 0x80) != 0) {
          temperature *= -1.0f;
        }

        return new DhtData(temperature, humidity);
      } catch (Exception e) {
        atempts++;
        if (atempts <= 3) {
          Gpio.delay(DHT_WAIT_INTERVAL);
          continue;
        }
        throw new Exception("Atempts " + atempts, e);
      }
    }
  }

  @Override
  public String toString() {
    return "DHT22, pin: " + getPin();
  }
}
