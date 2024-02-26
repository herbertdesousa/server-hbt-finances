package com.example.models

import org.jetbrains.exposed.dao.id.EntityID

/*
enum class FinanceRelativeAt {
  START_MONTH,
  END_MONTH
}

object Finances : BaseTable("finances") {
  val title = varchar("title", 64)
  val relativeAt = enumerationByName("relative_at", 11, FinanceRelativeAt::class)
  val distanceDays = integer("distance_days")
  val amount = float("amount")
}

data class Finance(
  val _id: EntityID<Int>,
  var title: String,
  var relativeAt: FinanceRelativeAt,
  var distanceDays: Int,
  var amount: Float,
) : BaseEntity(_id, Finances) {
  companion object : BaseEntityClass<Finance>(Finances)
}
*/
