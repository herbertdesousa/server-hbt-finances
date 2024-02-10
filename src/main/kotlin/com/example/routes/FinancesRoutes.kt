package com.example.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.time.LocalDate
import kotlin.time.Duration.Companion.days

@Serializable
data class FinancesDaysResponse(
  val todayInMonth: Int,
  val days: List<Int>,
)

fun Routing.financesRouting() {
  route("/finances") {
    get("/days") {
      val dayOfMonth = LocalDate.now().dayOfMonth

      val totalDaysOfMonth = LocalDate.now().lengthOfMonth()
      val daysOfMonth = List(totalDaysOfMonth) { it + 1 }

      call.respond(FinancesDaysResponse(dayOfMonth, daysOfMonth))
    }
  }
}