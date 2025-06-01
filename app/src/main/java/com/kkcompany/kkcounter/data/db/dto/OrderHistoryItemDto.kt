package com.kkcompany.kkcounter.data.db.dto

data class OrderHistoryItemDto(
    val id: Long,
    val tableId: Long,
    val tableName: String,
    val productId: Long,
    val productName: String,
    val productPrice: Int,
    val quantity: Int,
    val price: Int,
    val orderedAt: Long
)