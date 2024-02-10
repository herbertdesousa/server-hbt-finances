package com.example.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

fun currentUtc(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

abstract class BaseTable(name: String) : IntIdTable(name) {
  val createdAt = datetime("created_at").clientDefault { currentUtc() }
  val updatedAt = datetime("updated_at").nullable()
}

abstract class BaseEntity(id: EntityID<Int>, table: BaseTable) : IntEntity(id) {
  val createdAt by table.createdAt
  var updatedAt by table.updatedAt
}

abstract class BaseEntityClass<E : BaseEntity>(table: BaseTable) : IntEntityClass<E>(table) {
  init {
    EntityHook.subscribe { action ->
      if (action.changeType == EntityChangeType.Updated) {
        try {
          action.toEntity(this)?.updatedAt = currentUtc()
        } catch (e: Exception) {

        }
      }
    }
  }
}

