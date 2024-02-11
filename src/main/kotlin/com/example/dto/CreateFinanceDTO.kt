package com.example.dto

import com.example.models.FinanceRelativeAt
import kotlinx.serialization.Serializable

@Serializable
data class CreateFinanceDTO(
  var title: String = "",
  var relativeAt: FinanceRelativeAt = FinanceRelativeAt.START_MONTH,
  var distanceDays: Int = -1,
  var amount: Float = -1f,
)
