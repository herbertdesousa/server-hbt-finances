package com.example.routes

import com.example.routes.finances.financesRouting
import io.ktor.server.routing.*

fun Routing.routes() {
  financesRouting()
}