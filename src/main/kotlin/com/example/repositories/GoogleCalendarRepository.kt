package com.example.repositories

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class GoogleCalendar(val id: String, val summary: String)

@Serializable
data class GoogleEventDate(val date: String)

@Serializable
data class GoogleEvent(
  val id: String,
  val summary: String,
  val start: GoogleEventDate,
  val end: GoogleEventDate,
)

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

  fun createEvent(
    summary: String,
    startDateString: String,
    endDateString: String,
    recurrence: String? = null
  ): GoogleEvent = runBlocking {
    val response = client
      .post("calendars/${calendarId}/events") {
        @Serializable
        data class RequestDate(val date: String)

        @Serializable
        data class Request(
          val summary: String,
          val start: RequestDate,
          val end: RequestDate,
          val recurrence: List<String>
        )

        contentType(ContentType.Application.Json)
        setBody(
          Request(
            summary,
            start = RequestDate(startDateString),
            end = RequestDate(endDateString),
            recurrence = if (recurrence == null) emptyList() else listOf(recurrence)
          )
        )
      }
      .body<GoogleEvent>()

    response
  }
}

val googleCalendarRepository = GoogleCalendarRepository()
