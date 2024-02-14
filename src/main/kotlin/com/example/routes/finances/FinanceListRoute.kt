package com.example.routes.finances

import com.example.routes.googleCalendarRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.financeListRoute() {
  get {
    val events = googleCalendarRepository.listEvents()

    call.respond(events)
  }
}