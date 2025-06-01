package com.kkcompany.kkcounter.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_info")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tableId: Long,
    val productId: Long,
    val quantity: Int,
    val orderedAt: Long = System.currentTimeMillis()
)