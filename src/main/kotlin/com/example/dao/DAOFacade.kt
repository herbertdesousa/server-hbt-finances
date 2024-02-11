package com.example.dao

import com.example.dto.CreateFinanceDTO
import com.example.models.Finance
import com.example.models.FinanceRelativeAt

interface DAOFacade {
  suspend fun allFinances(): List<Finance>
  suspend fun createFinance(createFinanceDTO: CreateFinanceDTO): Finance?
}