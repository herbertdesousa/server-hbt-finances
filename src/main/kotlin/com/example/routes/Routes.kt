package com.example.routes

//import com.example.routes.finances.financesRouting
import com.example.dao.calendar.calendarDAO
import com.example.dto.CreateCalendarEventDTO
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
                LocalDateTime(2024, 2, 1, 0, 0),
            )
        )

        call.respond("hello world")
    }
}