package com.kkcompany.kkcounter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkcompany.kkcounter.data.repository.OrderHistoryRepository
import com.kkcompany.kkcounter.data.repository.OrderRepository
import com.kkcompany.kkcounter.ui.model.CombinedOrderDisplay
import com.kkcompany.kkcounter.utils.CombineOrderMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CombinedOrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val historyRepository: OrderHistoryRepository,
    private val combineOrderMapper: CombineOrderMapper
) : ViewModel() {

    private val _combinedOrders = MutableStateFlow<List<CombinedOrderDisplay>>(emptyList())
    val combinedOrders: StateFlow<List<CombinedOrderDisplay>> = _combinedOrders

    fun loadAllCombinedOrders() {
        viewModelScope.launch {
            val current = orderRepository.getAllOrderDisplayItems()
            val histories = historyRepository.getAll()

            _combinedOrders.update {
                combineOrderMapper.map(current, histories)
            }
        }
    }
}