package com.smarthome.SmartHome.service.impl

import com.smarthome.SmartHome.controller.model.TimeRange
import com.smarthome.SmartHome.repository.BoilerScheduleRepository
import com.smarthome.SmartHome.repository.RaspberryRepository
import com.smarthome.SmartHome.repository.entity.BoilerSchedule
import com.smarthome.SmartHome.repository.entity.Raspberry
import com.smarthome.SmartHome.service.BoilerScheduleService
import com.smarthome.SmartHome.service.PinService
import com.smarthome.SmartHome.service.impl.pin.model.SensorToPin
import com.smarthome.SmartHome.util.toHourAndMinute
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.TimeUnit


@Service
class BoilerScheduleServiceImpl @Autowired constructor(
        private val boilerScheduleRepository: BoilerScheduleRepository,
        private val raspberryRepository: RaspberryRepository,
        private val pinService: PinService
) : BoilerScheduleService {
    private val timers: MutableList<Timer> = mutableListOf()
    private val log = LoggerFactory.getLogger("BoilerScheduleService")

    @Transactional
    override fun getBoilerTimeRanges(): List<TimeRange> =
            boilerScheduleRepository.findAll().map { TimeRange(it.startMinute, it.endMinute) }

    @Transactional
    override fun updateBoilerTimeRanges(timeRanges: List<TimeRange>) {
        boilerScheduleRepository.deleteAll()
        boilerScheduleRepository.saveAll(timeRanges.map { BoilerSchedule(startMinute = it.startTime, endMinute = it.endTime) })
        initRecurringJobForEnablingBoiler()
    }

    @Transactional
    override fun enableBoiler(): Boolean {
        val optionalRaspberry = raspberryRepository.findById(1L)

        val newRaspberry = optionalRaspberry.orElse(Raspberry(1L, isBoilerEnabled = true))
                .copy(isBoilerEnabled = true)

        raspberryRepository.save(newRaspberry)
        initRecurringJobForEnablingBoiler()

        return true
    }

    @Transactional
    override fun disableBoiler(): Boolean {
        val optionalRaspberry = raspberryRepository.findById(1L)
        val newRaspberry = optionalRaspberry.orElse(Raspberry(1L))
                .copy(isBoilerEnabled = false)

        raspberryRepository.save(newRaspberry)
        initRecurringJobForEnablingBoiler()

        return false
    }

    @Transactional(readOnly = true)
    override fun isBoilerEnabled(): Boolean {
        return raspberryRepository.findById(1L).orElse(Raspberry(1L)).isBoilerEnabled
    }

    @Transactional
    override fun enableAllDayBoilerSchedule(): Boolean {
        val optionalRaspberry = raspberryRepository.findById(1L)

        val newRaspberry = optionalRaspberry.orElse(Raspberry(1L, isBoilerAllDayEnabled = true))
                .copy(isBoilerAllDayEnabled = true)

        raspberryRepository.save(newRaspberry)
        initRecurringJobForEnablingBoiler()

        return true
    }

    @Transactional
    override fun disableAllDayBoilerSchedule(): Boolean {
        val optionalRaspberry = raspberryRepository.findById(1L)
        val newRaspberry = optionalRaspberry.orElse(Raspberry(1L))
                .copy(isBoilerAllDayEnabled = false)

        raspberryRepository.save(newRaspberry)
        initRecurringJobForEnablingBoiler()

        return false
    }

    @Transactional(readOnly = true)
    override fun isAllDayBoilerScheduleEnabled(): Boolean {
        return raspberryRepository.findById(1L).orElse(Raspberry(1L)).isBoilerAllDayEnabled
    }

    override fun initRecurringJobForEnablingBoiler() {
        val isBoilerEnabled = isBoilerEnabled()
        val isAllDayBoilerScheduleEnabled = isAllDayBoilerScheduleEnabled()
        val boilerSchedule = getBoilerTimeRanges()

        timers.forEach { it.cancel() }

        log.info("Setting up recurring job for boiler. IsBoilerEnabled: $isBoilerEnabled, " +
                "IsAllDayScheduleEnabled: $isAllDayBoilerScheduleEnabled, " +
                "timeRangesCount: ${boilerSchedule.size}")

        if (isBoilerEnabled) {
            if (isAllDayBoilerScheduleEnabled) {
                log.info("Boiler enabled full time")
                pinService.setMultipurposeSensor(SensorToPin.BOILER_OUTPUT, true)
            } else {
                val timeRangesText = boilerSchedule.mapIndexed { index, timeRange ->
                    "${index+1}) ${timeRange.startTime.toHourAndMinute()} - ${timeRange.endTime.toHourAndMinute()}"
                }.joinToString("\n")
                log.info("Boiler enabled with following schedule:\n$timeRangesText")

                if(boilerSchedule.isEmpty()) {
                    log.info("Boiler disabled")
                    pinService.setMultipurposeSensor(SensorToPin.BOILER_OUTPUT, false)
                } else {
                    boilerSchedule.forEachIndexed { index, timeRange ->
                        timers.addAll(listOf(
                                getTimerWithMinuteScheduledEveryDay(index, timeRange.startTime, true),
                                getTimerWithMinuteScheduledEveryDay(index, timeRange.endTime, false)
                        ))
                    }
                }
            }
        } else {
            log.info("Boiler disabled")
            pinService.setMultipurposeSensor(SensorToPin.BOILER_OUTPUT, false)
        }
    }

    private fun getTimerWithMinuteScheduledEveryDay(index: Int, minutes: Int, doEnable: Boolean): Timer {
        val today = Calendar.getInstance()
        today[Calendar.HOUR_OF_DAY] = minutes / 60
        today[Calendar.MINUTE] = minutes % 60
        today[Calendar.SECOND] = 0

        return Timer("BoilerSchedule$index.enable:$doEnable").apply {
            schedule(
                    getBoilerEnableDisableTimerTask(doEnable),
                    today.time, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
            )
        }
    }

    private fun getBoilerEnableDisableTimerTask(doEnable: Boolean) =
            object : TimerTask() {
                override fun run() {
                    pinService.setMultipurposeSensor(SensorToPin.BOILER_OUTPUT, doEnable)
                }
            }
}
