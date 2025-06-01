package com.kkcompany.kkcounter.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orderHistory_info")
data class OrderHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tableId: Long,
    val itemsJson: String,
    val totalPrice: Int,
    val completedAt: Long = System.currentTimeMillis()
)