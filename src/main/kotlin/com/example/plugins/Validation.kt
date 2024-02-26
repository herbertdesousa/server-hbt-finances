package com.example.plugins

//import com.example.dto.CreateFinanceDTO
import com.example.dto.CreateCalendarEventDTO
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

fun Application.configureValidation() {
  install(RequestValidation) {
    /*validate<CreateFinanceDTO> { finance ->
      if (finance.summary.isBlank()) {
        return@validate ValidationResult.Invalid("Title is Required")
      }

      val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

      try {
        dateFormatter.parse(finance.dateString)
      } catch (e: Exception) {
        return@validate ValidationResult.Invalid("Invalid Date String")
      }

      ValidationResult.Valid
    }*/
  }
}