package com.example.routes

import com.example.dao.dao
import com.example.dto.CreateFinanceDTO
import com.example.dto.FinanceDTO
import com.example.repositories.GoogleCalendarRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.time.LocalDate

/*
@Serializable
data class FinancesDaysResponse(
  val todayInMonth: Int,
  val days: List<Int>,
)

fun Routing.financesRouting2() {
  route("/finances") {
    get {
      val finances: List<FinanceDTO> = dao.allFinances().map {
        FinanceDTO(
          id = it._id.value,
          title = it.title,
          relativeAt = it.relativeAt,
          distanceDays = it.distanceDays,
          amount = it.amount,
        )
      }

      call.respond(finances)
    }

    post {
      val financeReq = call.receive<CreateFinanceDTO>()

      val finance: FinanceDTO? = dao.createFinance(financeReq)?.let {
        FinanceDTO(
          id = it._id.value,
          title = it.title,
          relativeAt = it.relativeAt,
          distanceDays = it.distanceDays,
          amount = it.amount,
        )
      }

      if (finance == null) {
        call.respond(mapOf("error" to "error when creating an finance"))
        return@post
      }

      call.respond(finance)
    }

    get("/days") {
      val dayOfMonth = LocalDate.now().dayOfMonth

      val totalDaysOfMonth = LocalDate.now().lengthOfMonth()
      val daysOfMonth = List(totalDaysOfMonth) { it + 1 }

      call.respond(FinancesDaysResponse(dayOfMonth, daysOfMonth))
    }
  }
}
*/
