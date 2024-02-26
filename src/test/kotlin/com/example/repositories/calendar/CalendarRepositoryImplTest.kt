package com.example.repositories.calendar

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class CalendarRepositoryImplTest {
    @Test
    fun createAndListInDayTest() {
        val repository = CalendarRepositoryImpl()

        val event1 = CalendarEvent(summary = "titulo", rrule = "FREQ=DAILY;COUNT=5;WKST=MO;")
        val event2 = CalendarEvent(summary = "titulo2", rrule = "FREQ=DAILY;COUNT=2;WKST=MO;")
        val event3 = CalendarEvent(summary = "titulo3", rrule = "FREQ=DAILY;COUNT=8;WKST=MO;")

        val events = repository.listInDay(
            listOf(event1, event2, event3),
            LocalDateTime(2024, 1, 1, 0, 0, 0),
            LocalDateTime(2024, 1, 7, 0, 0, 0),
        )

        assertEquals(7, events.size)

        assertEquals("20240101T000000", events[0].day)
        assertEquals(event1.summary, events[0].events[0].summary)
        assertEquals(event2.summary, events[0].events[1].summary)
        assertEquals(event3.summary, events[0].events[2].summary)

        assertEquals("20240102T000000", events[1].day)
        assertEquals(event1.summary, events[1].events[0].summary)
        assertEquals(event2.summary, events[1].events[1].summary)
        assertEquals(event3.summary, events[1].events[2].summary)

        assertEquals("20240103T000000", events[2].day)
        assertEquals(event1.summary, events[2].events[0].summary)
        assertEquals(event3.summary, events[2].events[1].summary)

        assertEquals("20240104T000000", events[3].day)
        assertEquals(event1.summary, events[3].events[0].summary)
        assertEquals(event3.summary, events[3].events[1].summary)

        assertEquals("20240105T000000", events[4].day)
        assertEquals(event1.summary, events[4].events[0].summary)
        assertEquals(event3.summary, events[4].events[1].summary)

        assertEquals("20240106T000000", events[5].day)
        assertEquals(event3.summary, events[5].events[0].summary)

        assertEquals("20240107T000000", events[6].day)
        assertEquals(event3.summary, events[6].events[0].summary)
    }
}