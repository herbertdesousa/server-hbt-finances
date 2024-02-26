package com.example.routes.finances

import com.example.dao.calendar.calendarDAO
import com.example.dto.CreateCalendarEventDTO
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val id: String,
    val label: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime? = null,
)

fun Route.financeCreateRoute() {
    post {
        val body = call.receive<CreateCalendarEventDTO>()

        val createdEvent = calendarDAO.createCalendarEvent(body) ?: throw Exception()

        call.respond(Response(
            id = createdEvent._id.value.toString(),
            label = createdEvent.label,
            startAt = createdEvent.startAt,
            endAt = createdEvent.endAt,
        ))
    }
}
