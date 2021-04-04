package com.smarthome.SmartHome.service.impl.temperature_humidity.model



enum class DS18B20Type(val code: Int, val sensorName: String) {
    SENSOR_KITCHEN(1, "28-01193399277b"),
    SENSOR_LIVING_ROOM(2, "28-01193943427e"),
    SENSOR_BEDROOM(3, "28-0119337d0ec5"),
    SENSOR_OUTDOOR(4, "28-0119366960cc"),
    UNDEFINED(-1, "");

    companion object {
        fun getTypeByName(sensorName: String): DS18B20Type {
            values().forEach {
                if (sensorName == it.sensorName) return it
            }
            return UNDEFINED
        }
    }
}