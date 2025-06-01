package com.kkcompany.kkcounter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import com.kkcompany.kkcounter.data.repository.ProductRepository
import com.kkcompany.kkcounter.ui.model.enums.SortColumn
import com.kkcompany.kkcounter.ui.state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    var uiState by mutableStateOf(ProductUiState())
        private set

    private val _products = MutableStateFlow<List<ProductEntity>>(emptyList())
    val products: StateFlow<List<ProductEntity>> = _products.asStateFlow()

    var sortColumn by mutableStateOf(SortColumn.NAME)
        private set

    var isAscending by mutableStateOf(true)
        private set

    var showSnackbar by mutableStateOf(false)
        private set

    private var originalProducts: List<ProductEntity> = emptyList()

    init {
        fetchProducts()
    }

    fun updateUiState(name: String? = null, price: String? = null, description: String? = null) {
        uiState = uiState.copy(
            name = name ?: uiState.name,
            price = price ?: uiState.price,
            description = description ?: uiState.description
        )
    }

    fun addProduct() {
        val priceInt = uiState.price.toIntOrNull()
        if (uiState.name.isNotEmpty() && priceInt != null) {
            viewModelScope.launch {
                val product = ProductEntity(
                    name = uiState.name,
                    price = priceInt,
                    description = uiState.description,
                )
                productRepository.insert(product)
                fetchProducts()
                uiState = ProductUiState()
                showSnackbar = true
            }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            val fetched = productRepository.getAll()
            originalProducts = fetched
            _products.value = fetched
            applySorting()
        }
    }

    private fun applySorting() {
        viewModelScope.launch {
            val sortedList = originalProducts.sortedWith(
                compareBy<ProductEntity> {
                    when (sortColumn) {
                        SortColumn.NAME -> it.name.lowercase()
                        SortColumn.PRICE -> it.price
                        SortColumn.DESCRIPTION -> it.description.lowercase()
                    }
                }.let { if (isAscending) it else it.reversed() }
            )
            _products.emit(sortedList)
        }
    }

    fun sortProducts(column: SortColumn, ascending: Boolean) {
        sortColumn = column
        isAscending = ascending
        applySorting()
    }

    fun snackbarShown() {
        showSnackbar = false
    }
}