package com.example.dao.calendar

import com.example.dao.DatabaseSingleton.dbQuery
import com.example.dto.CalendarEventByDay
import com.example.dto.CreateCalendarEventDTO
import com.example.dto.CreateCalendarEventRecurrenceDTO
import com.example.models.CalendarEvent
import com.example.models.CalendarEventRecurrence
import com.example.models.CalendarEventRecurrences
import com.example.models.CalendarEvents
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

class CalendarDAOImpl : CalendarDAO {
    private fun eventRowToCalendarEvent(row: ResultRow) = CalendarEvent(
        _id = row[CalendarEvents.id],
        label = row[CalendarEvents.label],
        startAt = row[CalendarEvents.startAt],
        endAt = row[CalendarEvents.endAt],
    )

    private fun eventRecurrenceRowToCalendarEventRecurrence(row: ResultRow) = CalendarEventRecurrence(
        _id = row[CalendarEventRecurrences.id],
        frequency = row[CalendarEventRecurrences.frequency],
        interval = row[CalendarEventRecurrences.interval],
        count = row[CalendarEventRecurrences.count],
        byDay = emptyList()
    )

    override suspend fun createCalendarEvent(
        payload: CreateCalendarEventDTO
    ): CalendarEvent? = dbQuery {
        val insert = CalendarEvents.insert {
            it[label] = payload.label
            it[startAt] = payload.startAt
            it[endAt] = payload.endAt
        }

        insert.resultedValues?.singleOrNull()?.let(::eventRowToCalendarEvent)
    }

    override suspend fun createCalendarEvent(
        payload: CreateCalendarEventDTO,
        recurrences: List<CreateCalendarEventRecurrenceDTO>
    ): Unit = dbQuery {
        val insertedEvent = CalendarEvents.insert {
            it[label] = payload.label
            it[startAt] = payload.startAt
            it[endAt] = payload.endAt
        }

        val insertedEventRecurrences = recurrences.map { recurrence ->
            val insertedRecurrence = CalendarEventRecurrences.insert {
                it[calendarEvent] = insertedEvent[CalendarEvents.id].value
                it[frequency] = recurrence.frequency
                it[interval] = recurrence.interval
                it[count] = recurrence.count

                val day = recurrence.byDay
                it[byMonday] = day.find { it == CalendarEventByDay.MO } != null
                it[byTuesday] = day.find { it == CalendarEventByDay.TU } != null
                it[byWednesday] = day.find { it == CalendarEventByDay.WE } != null
                it[byThursday] = day.find { it == CalendarEventByDay.TH } != null
                it[byFriday] = day.find { it == CalendarEventByDay.FR } != null
                it[bySaturday] = day.find { it == CalendarEventByDay.SA } != null
                it[bySunday] = day.find { it == CalendarEventByDay.SU } != null
            }

            insertedRecurrence.resultedValues?.singleOrNull()?.let(::eventRecurrenceRowToCalendarEventRecurrence)
        }

        println(insertedEventRecurrences)

        insertedEvent.resultedValues?.singleOrNull()?.let(::eventRowToCalendarEvent)
    }
}

val calendarDAO: CalendarDAO = CalendarDAOImpl()