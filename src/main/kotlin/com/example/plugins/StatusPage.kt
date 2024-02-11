package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
data class RequestValidationExceptionRes(val message: String) {
  val error = "ValidationError";
}

fun Application.configureStatusPage() {
  install(StatusPages) {
    exception<RequestValidationException> { call, cause ->
      print("\n\nexception123: $cause\n\n")

      call.respond(HttpStatusCode.BadRequest, RequestValidationExceptionRes(cause.reasons.joinToString()))
    }
  }
}