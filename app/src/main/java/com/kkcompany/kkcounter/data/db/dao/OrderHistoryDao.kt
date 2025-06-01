package com.kkcompany.kkcounter.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kkcompany.kkcounter.data.db.entities.OrderHistoryEntity

@Dao
interface OrderHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: OrderHistoryEntity)

    @Query("SELECT * FROM orderHistory_info ORDER BY completedAt DESC")
    suspend fun getAll(): List<OrderHistoryEntity>

    @Query("SELECT * FROM orderHistory_info WHERE tableId = :tableId ORDER BY completedAt DESC")
    suspend fun getHistoriesForTable(tableId: Long): List<OrderHistoryEntity>
}