package com.smarthome.smarthome.util

fun Int.toHourAndMinute(): String = "${this / 60}:${this % 60}"