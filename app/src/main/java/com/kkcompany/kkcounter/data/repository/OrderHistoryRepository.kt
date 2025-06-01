package com.kkcompany.kkcounter.data.repository

import com.kkcompany.kkcounter.data.db.dao.OrderHistoryDao
import com.kkcompany.kkcounter.data.db.entities.OrderHistoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderHistoryRepository @Inject constructor(
    private val historyOrderDao: OrderHistoryDao
) {
    suspend fun insert(history: OrderHistoryEntity) = historyOrderDao.insert(history)
    suspend fun getHistory(tableId: Long) = historyOrderDao.getHistoriesForTable(tableId)
    suspend fun getAll(): List<OrderHistoryEntity> {
        return historyOrderDao.getAll()
    }
}