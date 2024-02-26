package com.example.dto

import com.example.utils.ResultValidation
import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

enum class CalendarEventFrequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

enum class CalendarEventByDay {
    MO,
    TU,
    WE,
    TH,
    FR,
    SA,
    SU,
}

@Serializable
data class CreateCalendarEventRecurrence(
    val frequency: CalendarEventFrequency = CalendarEventFrequency.WEEKLY,
    var interval: Int = 1,
    var count: Int? = null,
    val byDay: List<CalendarEventByDay> = emptyList()
)

@Serializable
open class CreateCalendarEventDTO (
    val label: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime? = null,
    val recurrences: List<CreateCalendarEventRecurrence> = emptyList()
)  {
    init {
        if (label.isBlank()) {
            throw ResultValidation.Error("Label is Required")
        }

        if (endAt != null) {
            val isEndAfterStart = endAt.toJavaLocalDateTime().isBefore(startAt.toJavaLocalDateTime())

            if (isEndAfterStart) throw ResultValidation.Error("startAt should be before endAt")
        }

        recurrences.forEachIndexed { index, it ->
            if (it.interval <= 0) recurrences[index].interval = 1
            if (it.count !== null && it.count!! <= 0) recurrences[index].count = null
        }
    }
}