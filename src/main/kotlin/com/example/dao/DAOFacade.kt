package com.example.dao

import com.example.models.Finance
import com.example.models.FinanceRelativeAt

interface DAOFacade {
  suspend fun allFinances(): List<Finance>
  suspend fun createFinance(
    title: String,
    relativeAt: FinanceRelativeAt,
    distanceDays: Int,
    amount: Float,
  ): Finance?
}