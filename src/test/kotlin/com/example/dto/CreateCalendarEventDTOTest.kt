package com.example.dto

import com.example.utils.ResultValidation
import kotlinx.datetime.LocalDateTime
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CreateCalendarEventDTOTest {
    @Test
    fun createCalendarEventDTO() {
        CreateCalendarEventDTO(
            label = "event",
            startAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
            endAt = LocalDateTime(2024, 1, 2, 0, 0, 0),
            recurrences = listOf(
                CreateCalendarEventRecurrence(
                    interval = 1,
                    byDay = listOf(CalendarEventByDay.MO),
                    frequency = CalendarEventFrequency.DAILY,
                    count = 2,
                )
            )
        )
    }

    @Test
    fun validateEmptyLabelTest() {
        assertFailsWith(ResultValidation.Error::class) {
            CreateCalendarEventDTO(
                label = "",
                startAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
            )
        }
    }

    @Test
    fun validateStartAtBeforeEndAtTest() {
        assertFailsWith(ResultValidation.Error::class) {
            CreateCalendarEventDTO(
                label = "event",
                startAt = LocalDateTime(2024, 1, 2, 0, 0, 0),
                endAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
            )
        }
    }

    @Test
    fun validateRecurrenceIntervalLessEq0Test() {
        val result1 = CreateCalendarEventDTO(
            label = "event",
            startAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
            endAt = LocalDateTime(2024, 1, 2, 0, 0, 0),
            recurrences = listOf(CreateCalendarEventRecurrence(interval = 0))
        )
        assertEquals(1, result1.recurrences[0].interval)

        val result2 = CreateCalendarEventDTO(
            label = "event",
            startAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
            endAt = LocalDateTime(2024, 1, 2, 0, 0, 0),
            recurrences = listOf(CreateCalendarEventRecurrence(interval = -1))
        )
        assertEquals(1, result2.recurrences[0].interval)
    }

    @Test
    fun validateRecurrenceCountLessEq0Test() {
        val result1 = CreateCalendarEventDTO(
            label = "event",
            startAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
            endAt = LocalDateTime(2024, 1, 2, 0, 0, 0),
            recurrences = listOf(CreateCalendarEventRecurrence(count = 0))
        )
        assertEquals(null, result1.recurrences[0].count)

        val result2 = CreateCalendarEventDTO(
            label = "event",
            startAt = LocalDateTime(2024, 1, 1, 0, 0, 0),
            endAt = LocalDateTime(2024, 1, 2, 0, 0, 0),
            recurrences = listOf(CreateCalendarEventRecurrence(count = -1))
        )
        assertEquals(null, result2.recurrences[0].count)
    }
}