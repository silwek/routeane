package com.silwek.routeane

import android.os.Build
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Calendar

fun getTodayDayOfWeek(): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val today = LocalDate.now().dayOfWeek
        return when (today) {
            DayOfWeek.MONDAY -> 1
            DayOfWeek.TUESDAY -> 2
            DayOfWeek.WEDNESDAY -> 3
            DayOfWeek.THURSDAY -> 4
            DayOfWeek.FRIDAY -> 5
            DayOfWeek.SATURDAY -> 6
            DayOfWeek.SUNDAY -> 7
            else -> 1
        }
    } else {
        val calendar = Calendar.getInstance()
        val javaDay = calendar.get(Calendar.DAY_OF_WEEK) // 1 = Sunday ... 7 = Saturday

        return when (javaDay) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> 1 // fallback
        }
    }

}