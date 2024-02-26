package com.example.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
class CreateCalendarEventDTO (
    val label: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime? = null,
)