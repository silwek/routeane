package com.silwek.routeane

import android.os.Build
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

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

fun getDayName(dayOfWeek: Int, everyDayStr: String): String {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dayOfWeekLD = when (dayOfWeek) {
            1 -> DayOfWeek.MONDAY
            2 -> DayOfWeek.TUESDAY
            3 -> DayOfWeek.WEDNESDAY
            4 -> DayOfWeek.THURSDAY
            5 -> DayOfWeek.FRIDAY
            6 -> DayOfWeek.SATURDAY
            7 -> DayOfWeek.SUNDAY
            else -> null
        }
        return dayOfWeekLD?.getDisplayName(TextStyle.FULL, Locale.getDefault()) ?: everyDayStr
    } else {
        val calendar = Calendar.getInstance()
        val dayOfWeekCal = when (dayOfWeek) {
            1 -> Calendar.MONDAY
            2 -> Calendar.TUESDAY
            3 -> Calendar.WEDNESDAY
            4 -> Calendar.THURSDAY
            5 -> Calendar.FRIDAY
            6 -> Calendar.SATURDAY
            7 -> Calendar.SUNDAY
            else -> null
        }
        if (dayOfWeekCal == null) return everyDayStr
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeekCal)
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            ?: "?"

    }
}