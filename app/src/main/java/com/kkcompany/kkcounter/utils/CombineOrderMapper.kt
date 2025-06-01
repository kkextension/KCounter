package com.kkcompany.kkcounter.utils

import android.util.Log
import com.kkcompany.kkcounter.data.db.entities.OrderHistoryEntity
import com.kkcompany.kkcounter.ui.model.CombinedOrderDisplay
import com.kkcompany.kkcounter.ui.model.OrderDisplayItem
import java.util.Date
import javax.inject.Inject

class CombineOrderMapper @Inject constructor(){
    fun map(currentOrders: List<OrderDisplayItem>, histories: List<OrderHistoryEntity>): List<CombinedOrderDisplay> {
        val currentCombined = currentOrders
            .groupBy { it.tableId }
            .map { (tableId, orders) ->
                CombinedOrderDisplay(
                    tableId = tableId,
                    tableName = orders.firstOrNull()?.tableName ?: "",
                    items = orders,
                    totalPrice = orders.sumOf { it.price },
                    completedAt = null
                )
            }

        val historyCombined = histories.map {
            val items = parseItemsJson(it.itemsJson)
            CombinedOrderDisplay(
                tableId = it.tableId,
                tableName = items.firstOrNull()?.tableName ?: "",
                items = items,
                totalPrice = it.totalPrice,
                completedAt = Date(it.completedAt)
            )
        }

        return (currentCombined + historyCombined).sortedByDescending { it.completedAt?.time ?: Long.MAX_VALUE }
    }
}