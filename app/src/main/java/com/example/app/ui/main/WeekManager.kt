package com.example.app.ui.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)

class WeekManager {
    private val TAG = "ComprobarSemana"
    fun getCurrentWeek(): Pair<LocalDate, LocalDate> {
        val today = LocalDate.now()
        val startOfWeek = today.with(DayOfWeek.MONDAY)
        val endOfWeek = today.with(DayOfWeek.SUNDAY)
        Log.d(TAG, "getCurrentWeek: $startOfWeek - $endOfWeek")
        return Pair(startOfWeek, endOfWeek)
    }

    fun getPreviousWeek(): Pair<LocalDate, LocalDate> {
        val (startOfWeek, _) = getCurrentWeek()
        val startOfPreviousWeek = startOfWeek.minusWeeks(1)
        val endOfPreviousWeek = startOfPreviousWeek.with(DayOfWeek.SUNDAY)
        Log.d(TAG, "getPreviousWeek: $startOfPreviousWeek - $endOfPreviousWeek")
        return Pair(startOfPreviousWeek, endOfPreviousWeek)
    }

    fun getNextWeek(): Pair<LocalDate, LocalDate> {
        val (_, endOfWeek) = getCurrentWeek()
        val startOfNextWeek = endOfWeek.plusDays(1)
        val endOfNextWeek = startOfNextWeek.with(DayOfWeek.SUNDAY)
        Log.d(TAG, "getNextWeek: $startOfNextWeek - $endOfNextWeek")
        return Pair(startOfNextWeek, endOfNextWeek)
    }

    fun getDaysOfWeek(startDate: LocalDate): List<LocalDate> {
        val startOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endOfWeek = startDate.with(DayOfWeek.SUNDAY)

        val daysOfWeek = mutableListOf<LocalDate>()
        var currentDay = startOfWeek
        while (currentDay.isBefore(endOfWeek) || currentDay == endOfWeek) {
            daysOfWeek.add(currentDay)
            currentDay = currentDay.plusDays(1)
        }
        return daysOfWeek
    }
}