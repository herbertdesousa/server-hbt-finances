package com.example.repositories.calendar

import kotlinx.datetime.LocalDateTime

data class CalendarEvent(val summary: String, val rrule: String)

data class CalendarEventsInDay(val day: String, val events: List<CalendarEvent>)

interface CalendarRepository {
    fun listInDay(
        events: List<CalendarEvent>,
        startAt: LocalDateTime,
        endAt: LocalDateTime,
    ): List<CalendarEventsInDay>

//    fun createEvent(summary: String, rrule: String): CalendarEvent<OriginalEvent>
}
