package com.kkcompany.kkcounter.ui.state

import java.util.Date

data class ProductUiState(
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val date: Date = Date()
)