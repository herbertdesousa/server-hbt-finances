package com.example.dao

import com.example.models.Finances
import com.example.dao.DatabaseSingleton.dbQuery
import com.example.models.Finance
import com.example.models.FinanceRelativeAt
import com.example.models.currentUtc
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class DaoFacadeImpl : DAOFacade {
  private fun resultRowToFinance(row: ResultRow) = Finance(
    _id = row[Finances.id],
    title = row[Finances.title],
    relativeAt = row[Finances.relativeAt],
    distanceDays = row[Finances.distanceDays],
    amount = row[Finances.amount],
  )

  override suspend fun allFinances(): List<Finance> = dbQuery {
    Finances.selectAll().map(::resultRowToFinance)
  }

  override suspend fun createFinance(
    title: String,
    relativeAt: FinanceRelativeAt,
    distanceDays: Int,
    amount: Float
  ): Finance? = dbQuery {
    val insert = Finances.insert {
      it[Finances.title] = title
      it[Finances.relativeAt] = relativeAt
      it[Finances.distanceDays] = distanceDays
      it[Finances.amount] = amount
    }

    insert.resultedValues?.singleOrNull()?.let(::resultRowToFinance)
  }

}

val dao: DAOFacade = DaoFacadeImpl()
