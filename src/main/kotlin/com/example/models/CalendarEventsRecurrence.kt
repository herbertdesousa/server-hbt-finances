package com.example.models

import com.example.dto.CalendarEventByDay
import com.example.dto.CalendarEventFrequency
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

object CalendarEventRecurrences : BaseTable("calendar_event_recurrence") {
    val calendarEvent = reference("event", CalendarEvents, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val frequency =
        enumerationByName("frequency", 7, CalendarEventFrequency::class).default(CalendarEventFrequency.WEEKLY)
    val interval = integer("interval").default(1)
    val count = integer("count").nullable()
    val byMonday = bool("by_monday").default(false)
    val byTuesday = bool("by_tuesday").default(false)
    val byWednesday = bool("by_wednesday").default(false)
    val byThursday = bool("by_thursday").default(false)
    val byFriday = bool("by_friday").default(false)
    val bySaturday = bool("by_saturday").default(false)
    val bySunday = bool("by_sunday").default(false)
}

data class CalendarEventRecurrence(
    val _id: EntityID<UUID>,
    val frequency: CalendarEventFrequency,
    val interval: Int,
    val count: Int?,
    val byDay: List<CalendarEventByDay>
) : BaseEntity(_id, CalendarEvents) {
    companion object : BaseEntityClass<CalendarEvent>(CalendarEvents)
}