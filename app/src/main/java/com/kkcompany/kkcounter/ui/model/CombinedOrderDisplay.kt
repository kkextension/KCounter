package com.kkcompany.kkcounter.ui.model

import java.util.Date

data class CombinedOrderDisplay(
    val tableId: Long,
    val tableName: String,
    val items: List<OrderDisplayItem>,
    val totalPrice: Int,
    val completedAt: Date?
)