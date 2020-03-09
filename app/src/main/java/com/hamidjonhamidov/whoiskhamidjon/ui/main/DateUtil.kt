package com.hamidjonhamidov.whoiskhamidjon.ui.main

import com.hamidjonhamidov.whoiskhamidjon.util.Constants
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private val dateFormat = "dd/MM/yyyy hh:mm:ss"

    private val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)

    private fun getDateFromMillisecond(milliSeconds: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds

        return formatter.format(calendar.time)
    }

    private fun getMillisecondsFromDate(date: String) = formatter.parse(date).time

    fun getDifferenceWithCurrentDate(): String {
        val oldMSeconds = getMillisecondsFromDate(Constants.PROGRAMMING_EXPERIENCE_DATE)
        val currentMSeconds = System.currentTimeMillis()

        val difSeconds = (currentMSeconds - oldMSeconds) / 1000

        val years = (difSeconds / 3600 / 24 / 365).toInt()
        val months = ((difSeconds - years * 3600 * 24 * 365) / 3600 / 24 / 30).toInt()
        val days =
            ((difSeconds - years * 3600 * 24 * 365 - months * 30 * 24 * 3600) / 3600 / 24).toInt()
        val hours =
            ((difSeconds - years * 3600 * 24 * 365 - months * 30 * 24 * 3600 - days * 24 * 3600) / 3600).toInt()
        val minutes = ((
                difSeconds -
                        years * 3600 * 24 * 365 -
                        months * 30 * 24 * 3600 -
                        days * 24 * 3600 -
                        hours * 3600) / 60).toInt()

        val seconds = (
                difSeconds -
                        years * 3600 * 24 * 365 -
                        months * 30 * 24 * 3600 -
                        days * 24 * 3600 -
                        hours * 3600 - minutes*60).toInt()

        return "$years yrs(s) $months month(s) $days day(s)\n$hours hours $minutes min(s) $seconds sec(s) "
    }
}






















