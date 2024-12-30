package com.example.simple_clock

import java.util.*

object ClockHelper {
    val hours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private val calendar: Calendar get() = Calendar.getInstance()
    private val _time = Time()
    val time: Time
        get() = _time.apply {
            /*hour = 3f
            minute = 0f
            second = 0f*/
            hour = calendar[Calendar.HOUR_OF_DAY].toFloat()
            //convert to 12hour format from 24 hour format
            hour = if (hour > 12) hour - 12 else hour
            minute = calendar[Calendar.MINUTE].toFloat()
            second = calendar[Calendar.SECOND].toFloat()
        }
}