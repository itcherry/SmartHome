package com.smarthome.smarthome.service

import com.smarthome.smarthome.controller.model.TimeRange

interface BoilerScheduleService {
    fun getBoilerTimeRanges(): List<TimeRange>
    fun updateBoilerTimeRanges(timeRanges: List<TimeRange>)

    fun enableBoiler():Boolean
    fun disableBoiler():Boolean
    fun isBoilerEnabled(): Boolean

    fun enableAllDayBoilerSchedule():Boolean
    fun disableAllDayBoilerSchedule():Boolean
    fun isAllDayBoilerScheduleEnabled(): Boolean

    fun initRecurringJobForEnablingBoiler()
}