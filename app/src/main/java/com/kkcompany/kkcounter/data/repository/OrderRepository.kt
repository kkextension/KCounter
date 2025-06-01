package com.kkcompany.kkcounter.data.repository

import com.kkcompany.kkcounter.data.db.dao.OrderDao
import com.kkcompany.kkcounter.data.db.dao.ProductDao
import com.kkcompany.kkcounter.data.db.entities.OrderEntity
import com.kkcompany.kkcounter.ui.model.OrderDisplayItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val productDao: ProductDao,
    private val tableRepository: TableRepository
) {

    suspend fun insert(order: OrderEntity) {
        orderDao.insert(order)
    }

    suspend fun findByTableId(tableId: Long): List<OrderEntity> {
        return orderDao.getByTableId(tableId)
    }

    suspend fun delete(order: OrderEntity) {
        orderDao.delete(order)
    }

    suspend fun getAll() {
        orderDao.getAll()
    }

    suspend fun deleteByTableId(tableId: Long) {
        orderDao.deleteByTableId(tableId)
    }

    suspend fun findByTableIdAndProductId(tableId: Long, productId: Long): OrderEntity? {
        return orderDao.getByTableIdAndProductId(tableId, productId)
    }

    suspend fun updateQuantityById(orderId: Long, newQuantity: Int) {
        orderDao.updateQuantityById(orderId, newQuantity)
    }

    suspend fun getAllOrderDisplayItems(): List<OrderDisplayItem> {
        val orders = orderDao.getAll()
        val products = productDao.getAll().associateBy { it.id }
        val tableNames = orders
            .map { it.tableId }
            .distinct()
            .associateWith { tableRepository.findByTableId(it)?.name ?: "" }

        return orders.mapNotNull { order ->
            val product = products[order.productId] ?: return@mapNotNull null
            val tableName = tableNames[order.tableId] ?: ""
            OrderDisplayItem(
                id = order.id,
                tableId = order.tableId,
                productId = order.productId,
                tableName = tableName,
                productName = product.name,
                quantity = order.quantity,
                price = product.price * order.quantity,
                orderedAt = order.orderedAt
            )
        }.sortedByDescending { it.orderedAt }
    }
}