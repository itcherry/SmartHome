package com.smarthome.smarthome.controller

import com.smarthome.smarthome.controller.model.TimeRange
import com.smarthome.smarthome.service.BoilerScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import com.smarthome.smarthome.controller.model.ResponseBody

@RestController
class BoilerController @Autowired constructor(private val boilerService: BoilerScheduleService) {

    @RequestMapping(value = [BOILER_VALUE], method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun setBoilerState(@RequestParam(IS_ENABLE_FIELD) isEnabled: Boolean): ResponseBody<Boolean> {
        return ResponseBody(
            ResponseBody.SUCCESS, null,
            if (isEnabled) {
                boilerService.enableBoiler()
            } else {
                boilerService.disableBoiler()
            }
        )
    }

    @RequestMapping(value = [BOILER_VALUE], method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getBoilerState() = ResponseBody(
        ResponseBody.SUCCESS,
        null,
        boilerService.isBoilerEnabled()
    )

    @RequestMapping(value = [BOILER_SCHEDULE_STATE_VALUE], method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun setBoilerScheduleState(@RequestParam(IS_ENABLE_FIELD) isEnabled: Boolean): ResponseBody<Boolean> {
        return ResponseBody(
            ResponseBody.SUCCESS, null,
            if (isEnabled) {
                boilerService.enableAllDayBoilerSchedule()
            } else {
                boilerService.disableAllDayBoilerSchedule()
            }
        )

    }

    @RequestMapping(value = [BOILER_SCHEDULE_STATE_VALUE], method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getBoilerScheduleState() = ResponseBody(
        ResponseBody.SUCCESS,
        null,
        boilerService.isAllDayBoilerScheduleEnabled()
    )

    @RequestMapping(value = [BOILER_SCHEDULE_VALUE], method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun setBoilerSchedule(@RequestBody timeRanges: List<TimeRange>) {
        boilerService.updateBoilerTimeRanges(timeRanges)
    }

    @RequestMapping(value = [BOILER_SCHEDULE_VALUE], method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getBoilerSchedule() = ResponseBody(
        ResponseBody.SUCCESS,
        null,
        boilerService.getBoilerTimeRanges()
    )
}