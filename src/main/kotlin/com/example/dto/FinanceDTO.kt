package com.example.dto

import com.example.models.FinanceRelativeAt
import kotlinx.serialization.Serializable

@Serializable
data class FinanceDTO(
  val id: Int,
  var title: String,
  var relativeAt: FinanceRelativeAt,
  var distanceDays: Int,
  var amount: Float,
)