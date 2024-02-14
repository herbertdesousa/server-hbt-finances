package com.example.repositories

import com.example.routes.googleCalendarRepository
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class GoogleCalendar(val id: String, val summary: String)

@Serializable
data class GoogleEvent(val id: String)

const val TOKEN = ""

const val DEFAULT_CALENDAR_SUMMARY = "Vilela Ar - NÃO APAGAR AUTO GERADO"

class GoogleCalendarRepository {
  val client = httpClient.config {
    defaultRequest {
      url("https://www.googleapis.com/calendar/v3/")
      headers {
        bearerAuth(TOKEN)
      }
    }
  }

  var calendarId: String

  init {
    runBlocking {
      val savedCalendar = runBlocking {
        @Serializable
        data class Response(val items: List<GoogleCalendar>)

        client
          .get("users/me/calendarList")
          .body<Response>()
      }
        .let { calendarList ->
          val calendar = calendarList.items.find { it.summary == DEFAULT_CALENDAR_SUMMARY }

          if (calendar != null) return@let calendar

          client
            .post("calendars") {
              @Serializable
              data class Request(val summary: String)

              contentType(ContentType.Application.Json)
              setBody(Request(DEFAULT_CALENDAR_SUMMARY))
            }
            .body<GoogleCalendar>()
        }

      calendarId = savedCalendar.id
    }
  }

  fun listEvents(): List<GoogleEvent> = runBlocking {
    @Serializable
    data class Request(val items: List<GoogleEvent>)

    val response = client
      .get("calendars/${calendarId}/events")
      .body<Request>()

    response.items
  }

  fun createEvent() {

  }
}

val googleCalendarRepository = GoogleCalendarRepository()
