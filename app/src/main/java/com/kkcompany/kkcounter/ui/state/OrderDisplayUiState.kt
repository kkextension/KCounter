package com.kkcompany.kkcounter.ui.state

import com.kkcompany.kkcounter.ui.model.OrderDisplayItem

data class OrderDisplayUiState(
    val orders: List<OrderDisplayItem> = emptyList(),
    val totalPrice: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)