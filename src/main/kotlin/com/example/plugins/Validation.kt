package com.example.plugins

import com.example.dto.CreateFinanceDTO
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
  install(RequestValidation) {
    validate<CreateFinanceDTO> { finance ->
      if (finance.title.isBlank()) {
        ValidationResult.Invalid("title is required")
      } else if (finance.amount.isNaN() || finance.amount <= 0) {
        ValidationResult.Invalid("amount is required and must be higher than 0")
      } else if (finance.distanceDays > 20 || finance.distanceDays < 0) {
        ValidationResult.Invalid("finance is required and between 0 to 20")
      } else ValidationResult.Valid
    }
  }
}