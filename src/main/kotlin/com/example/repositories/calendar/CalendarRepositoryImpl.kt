package com.example.repositories.calendar

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import net.fortuna.ical4j.model.*
import net.fortuna.ical4j.model.component.CalendarComponent
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.DtStart
import net.fortuna.ical4j.model.property.RRule
import net.fortuna.ical4j.model.property.Summary
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

class CalendarRepositoryImpl : CalendarRepository {
    private fun serializeToVEvent(event: CalendarEvent): VEvent {
        val properties = PropertyList<Property>()

        properties.add(Summary(event.summary))
        properties.add(DtStart("20240101T030000Z"))
        properties.add(RRule(event.rrule))

        return VEvent(properties)
    }

    private fun deserializeToCalendarEvent(event: VEvent): CalendarEvent {
        return CalendarEvent(
            summary = event.summary.value,
            rrule = event.properties.getProperties<RRule>(Property.RRULE).toString(),
        )
    }

    override fun listInDay(
        events: List<CalendarEvent>,
        startAt: LocalDateTime,
        endAt: LocalDateTime,
    ): List<CalendarEventsInDay> {
        val serializedEvents = events.map { serializeToVEvent(it) }

        val calendar = Calendar(ComponentList(serializedEvents))

        val periodInDays = endAt.compareTo(startAt).days.toInt(DurationUnit.DAYS)

        return (0..periodInDays).map {
            val day = startAt
                .toJavaLocalDateTime()
                .plusDays(it.toLong())
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"))

            val startOfDay = day + "T000000"
            val endOfDay = day + "T235959"
            val period = Period(DateTime(startOfDay), DateTime(endOfDay))

            val filteredEvents = calendar.getComponents<VEvent>(CalendarComponent.VEVENT).filter { event ->
                event.calculateRecurrenceSet(period).size > 0
            }

            CalendarEventsInDay(
                startOfDay,
                filteredEvents.map { deserializeToCalendarEvent(it) }
            )
        }
    }
}