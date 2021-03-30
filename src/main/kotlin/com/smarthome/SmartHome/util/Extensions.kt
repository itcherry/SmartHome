package com.smarthome.SmartHome.util

fun Int.toHourAndMinute(): String = "${this / 60}:${this % 60}"