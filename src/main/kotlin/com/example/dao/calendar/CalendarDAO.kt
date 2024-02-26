package com.example.dao.calendar

import com.example.dto.CreateCalendarEventDTO
import com.example.models.CalendarEvent

interface CalendarDAO {
  suspend fun createCalendarEvent(payload: CreateCalendarEventDTO): CalendarEvent?
}