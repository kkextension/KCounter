package com.kkcompany.kkcounter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkcompany.kkcounter.data.db.dto.OrderHistoryItemDto
import com.kkcompany.kkcounter.data.db.entities.OrderEntity
import com.kkcompany.kkcounter.data.db.entities.OrderHistoryEntity
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import com.kkcompany.kkcounter.data.repository.OrderHistoryRepository
import com.kkcompany.kkcounter.data.repository.OrderRepository
import com.kkcompany.kkcounter.data.repository.ProductRepository
import com.kkcompany.kkcounter.data.repository.TableRepository
import com.kkcompany.kkcounter.ui.model.OrderDisplayItem
import com.kkcompany.kkcounter.ui.state.OrderDisplayUiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val orderHistoryRepository: OrderHistoryRepository,
    private val productRepository: ProductRepository,
    private val tableRepository: TableRepository
) : ViewModel() {

    private val _orderDisplayUiState = MutableStateFlow(OrderDisplayUiState())
    val orderDisplayUiState: StateFlow<OrderDisplayUiState> = _orderDisplayUiState.asStateFlow()

    var showSnackbar by mutableStateOf(false)
        private set

    fun loadOrdersForTable(tableId: Long) {
        viewModelScope.launch {
            _orderDisplayUiState.value = buildDisplayUiState(tableId)
        }
    }

    fun addOrUpdateOrder(tableId: Long, product: ProductEntity) {
        viewModelScope.launch {
            val existing = orderRepository.findByTableIdAndProductId(tableId, product.id)
            if (existing != null) {
                orderRepository.updateQuantityById(existing.id, existing.quantity + 1)
            } else {
                orderRepository.insert(
                    OrderEntity(
                        tableId = tableId,
                        productId = product.id,
                        quantity = 1
                    )
                )
            }
            refreshOrders(tableId)
        }
    }

    fun deleteOrder(order: OrderDisplayItem, tableId: Long) {
        val current = _orderDisplayUiState.value.orders.toMutableList()
        val index = current.indexOfFirst { it.id == order.id }
        if (index >= 0) {
            val updated = current[index]
            if (updated.quantity > 1) {
                current[index] = updated.copy(
                    quantity = updated.quantity - 1,
                    price = updated.price - (updated.price / updated.quantity)
                )
            } else {
                current.removeAt(index)
            }

            _orderDisplayUiState.value = _orderDisplayUiState.value.copy(
                orders = current,
                totalPrice = current.sumOf { it.price }
            )
        }

        viewModelScope.launch {
            if (order.quantity > 1) {
                orderRepository.updateQuantityById(order.id, order.quantity - 1)
            } else {
                orderRepository.delete(
                    OrderEntity(
                        id = order.id,
                        tableId = tableId,
                        productId = order.productId,
                        quantity = order.quantity
                    )
                )
            }
        }
    }

    fun completeOrder(tableId: Long) {
        viewModelScope.launch {
            val orders = orderRepository.findByTableId(tableId)
            if (orders.isNotEmpty()) {
                val productMap = getAllProductsMap()
                val tableName = tableRepository.findByTableId(tableId)?.name ?: ""

                val orderDtos = orders.mapNotNull { order ->
                    val product = productMap[order.productId]
                    product?.let {
                        OrderHistoryItemDto(
                            id = order.id,
                            tableId = tableId,
                            tableName = tableName,
                            productId = order.productId,
                            productPrice = product.price,
                            productName = it.name,
                            quantity = order.quantity,
                            price = it.price * order.quantity,
                            orderedAt = order.orderedAt
                        )
                    }
                }

                val totalPrice = orderDtos.sumOf { it.price }

                val history = OrderHistoryEntity(
                    tableId = tableId,
                    itemsJson = serializeOrdersToJson(orderDtos),
                    totalPrice = totalPrice
                )

                orderHistoryRepository.insert(history)
            }

            orderRepository.deleteByTableId(tableId)
            refreshOrders(tableId)
        }
    }

    fun <T> serializeOrdersToJson(items: List<T>): String {
        return Gson().toJson(items)
    }

    private suspend fun refreshOrders(tableId: Long) {
        _orderDisplayUiState.value = buildDisplayUiState(tableId)
    }

    private suspend fun buildDisplayUiState(tableId: Long): OrderDisplayUiState {
        val orders = orderRepository.findByTableId(tableId)
        val productMap = getAllProductsMap()
        val tableName = tableRepository.findByTableId(tableId)?.name ?: ""

        val displayOrders = orders.mapNotNull { it.toDisplayItem(productMap[it.productId], tableName) }

        return OrderDisplayUiState(
            orders = displayOrders,
            totalPrice = displayOrders.sumOf { it.price }
        )
    }

    private suspend fun getAllProductsMap(): Map<Long, ProductEntity> {
        return productRepository.getAll().associateBy { it.id }
    }

    private fun OrderEntity.toDisplayItem(product: ProductEntity?, tableName:String): OrderDisplayItem? {
        return product?.let {
            OrderDisplayItem(
                id = this.id,
                tableId = this.tableId,
                productId = this.productId,
                tableName = tableName,
                productName = it.name,
                quantity = this.quantity,
                price = it.price * this.quantity,
                orderedAt = this.orderedAt
            )
        }
    }
}
