package com.example.dto

import kotlinx.serialization.Serializable

enum class CalendarEventFrequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

enum class CalendarEventByDay {
    MO,
    TU,
    WE,
    TH,
    FR,
    SA,
    SU,
}

@Serializable
data class CreateCalendarEventRecurrenceDTO(
    val frequency: CalendarEventFrequency = CalendarEventFrequency.WEEKLY,
    val interval: Int = 1,
    val count: Int? = null,
    val byDay: List<CalendarEventByDay> = emptyList()
)