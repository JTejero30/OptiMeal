package com.example.app.ui.main.model

import java.time.LocalDate

data class DayModel(
    val calendarDayOfWeek: Int,
    val calendarMonth: String,
    val dayOfWeek: String,
    val year: Int,
    val isCurrentDay: Boolean,
    val day:LocalDate
    )
