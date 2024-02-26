package com.example.models

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.*

object CalendarEvents : BaseTable("calendar_events") {
    val label = varchar("label", 255)
    val startAt = datetime("start_at")
    val endAt = datetime("end_at").nullable()
}

data class CalendarEvent(
    val _id: EntityID<UUID>,
    val label: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime?,
) : BaseEntity(_id, CalendarEvents) {
    companion object : BaseEntityClass<CalendarEvent>(CalendarEvents)
}