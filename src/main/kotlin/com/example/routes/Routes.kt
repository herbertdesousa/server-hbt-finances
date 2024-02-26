package com.example.routes

//import com.example.routes.finances.financesRouting
import com.example.dao.calendar.calendarDAO
import com.example.dto.CalendarEventByDay
import com.example.dto.CalendarEventFrequency
import com.example.dto.CreateCalendarEventDTO
import com.example.dto.CreateCalendarEventRecurrenceDTO
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDateTime

fun Routing.routes() {
//  financesRouting()
    get {
        calendarDAO.createCalendarEvent(
            CreateCalendarEventDTO(
                "meu evento",
                LocalDateTime(2024, 1, 1, 0, 0),
            ),
            listOf(
                CreateCalendarEventRecurrenceDTO(
                    frequency = CalendarEventFrequency.DAILY,
                    interval = 2,
                    count = 5,
                    byDay = listOf(CalendarEventByDay.MO, CalendarEventByDay.TU)
                )
            )
        )

        calendarDAO.createCalendarEvent(
            CreateCalendarEventDTO(
                "meu evento 2",
                LocalDateTime(2024, 1, 1, 0, 0),
                LocalDateTime(2024, 2, 1, 0, 0),
            ),
            listOf(
                CreateCalendarEventRecurrenceDTO(
                    frequency = CalendarEventFrequency.WEEKLY,
                    interval = 1,
                )
            )
        )

        call.respond("hello world")
    }
}