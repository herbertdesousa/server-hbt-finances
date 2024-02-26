package com.example.dao.calendar

import com.example.dao.DatabaseSingleton.dbQuery
import com.example.dto.CreateCalendarEventDTO
import com.example.models.CalendarEvent
import com.example.models.CalendarEvents
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

class CalendarDAOImpl : CalendarDAO {
    private fun resultRowToFinance(row: ResultRow) = CalendarEvent(
        _id = row[CalendarEvents.id],
        label = row[CalendarEvents.label],
        startAt = row[CalendarEvents.startAt],
        endAt = row[CalendarEvents.endAt],
    )

    override suspend fun createCalendarEvent(
        payload: CreateCalendarEventDTO
    ): CalendarEvent? = dbQuery {
        val insert = CalendarEvents.insert {
            it[label] = payload.label
            it[startAt] = payload.startAt
            it[endAt] = payload.endAt
        }

        insert.resultedValues?.singleOrNull()?.let(::resultRowToFinance)
    }
}

val calendarDAO: CalendarDAO = CalendarDAOImpl()