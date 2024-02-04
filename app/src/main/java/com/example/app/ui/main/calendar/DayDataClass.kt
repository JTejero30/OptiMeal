package com.example.app.ui.main.calendar

data class DayDataClass(
    val dayOfMonthDc: Int,
    val monthDc: Int,
    val yearDc: Int,
    var isCurrentDayDc: Boolean = false,
    var isSelectedDc: Boolean = false

)
