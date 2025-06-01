package com.kkcompany.kkcounter.viewmodel

import app.cash.turbine.test
import com.kkcompany.kkcounter.data.db.entities.OrderEntity
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import com.kkcompany.kkcounter.data.repository.OrderHistoryRepository
import com.kkcompany.kkcounter.data.repository.OrderRepository
import com.kkcompany.kkcounter.data.repository.ProductRepository
import com.kkcompany.kkcounter.ui.model.OrderDisplayItem
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: OrderViewModel
    private val fakeOrderRepo = mockk<OrderRepository>(relaxed = true)
    private val fakeHistoryRepo = mockk<OrderHistoryRepository>(relaxed = true)
    private val fakeProductRepo = mockk<ProductRepository>(relaxed = true)

    private val sampleProduct = ProductEntity(1L, "Latte", 4000, "Hot")
    private val sampleOrder = OrderEntity(1L, tableId = 10L, productId = 1L, quantity = 1)

//    @Before
//    fun setup() {
//        viewModel = OrderViewModel(fakeOrderRepo, fakeHistoryRepo, fakeProductRepo)
//    }
//
//    @Test
//    fun `addOrUpdateOrder - adds new order if not exists`() = runTest {
//        coEvery { fakeOrderRepo.findByTableIdAndProductId(10L, 1L) } returns null
//        coEvery { fakeOrderRepo.findByTableId(10L) } returns listOf(sampleOrder)
//        coEvery { fakeProductRepo.getAll() } returns listOf(sampleProduct)
//
//        viewModel.addOrUpdateOrder(10L, sampleProduct)
//
//        coVerify { fakeOrderRepo.insert(match { it.tableId == 10L && it.productId == 1L && it.quantity == 1 }) }
//        viewModel.orderDisplayUiState.test {
//            val state = awaitItem()
//            assertEquals(4000, state.totalPrice)
//        }
//    }
//
//    @Test
//    fun `addOrUpdateOrder - updates quantity if order exists`() = runTest {
//        val updatedOrder = sampleOrder.copy(quantity = 2)
//        coEvery { fakeOrderRepo.findByTableIdAndProductId(10L, 1L) } returns sampleOrder
//        coEvery { fakeOrderRepo.findByTableId(10L) } returns listOf(updatedOrder)
//        coEvery { fakeProductRepo.getAll() } returns listOf(sampleProduct)
//
//        viewModel.addOrUpdateOrder(10L, sampleProduct)
//
//        coVerify { fakeOrderRepo.updateQuantityById(sampleOrder.id, 2) }
//        viewModel.orderDisplayUiState.test {
//            val state = awaitItem()
//            assertEquals(8000, state.totalPrice) // 4000 * 2
//        }
//    }
//
//    @Test
//    fun `deleteOrder - removes order and updates UI state`() = runTest {
//        coEvery { fakeOrderRepo.findByTableId(10L) } returns emptyList()
//        coEvery { fakeProductRepo.getAll() } returns listOf(sampleProduct)
//
//        val displayItem = OrderDisplayItem(
//            id = 1L, tableId = 10L, productId = 1L, productName = "Latte",
//            quantity = 1, price = 4000, orderedAt = System.currentTimeMillis()
//        )
//
//        viewModel.deleteOrder(displayItem, 10L)
//
//        coVerify { fakeOrderRepo.delete(any()) }
//
//        viewModel.orderDisplayUiState.test {
//            val state = awaitItem()
//            assertEquals(0, state.orders.size)
//            assertEquals(0, state.totalPrice)
//        }
//    }
//
//    @Test
//    fun `completeOrder - moves to history and clears orders`() = runTest {
//        val orderList = listOf(sampleOrder)
//
//        // 첫 번째 findByTableId 호출에 대한 응답 (ViewModel 내부에서 insert 전에 사용)
//        coEvery { fakeOrderRepo.findByTableId(10L) } returns orderList andThen emptyList()
//
//        coEvery { fakeProductRepo.getAll() } returns listOf(sampleProduct)
//        coEvery { fakeHistoryRepo.insert(any()) } just Runs
//        coEvery { fakeOrderRepo.deleteByTableId(10L) } just Runs
//
//        viewModel.completeOrder(10L)
//
//        coVerify { fakeHistoryRepo.insert(match { it.totalPrice == 4000 }) }
//        coVerify { fakeOrderRepo.deleteByTableId(10L) }
//
//        viewModel.orderDisplayUiState.test {
//            val state = awaitItem()
//            assertEquals(0, state.totalPrice)
//        }
//    }
//
//    @Test
//    fun `loadOrdersForTable - loads correct data and emits UI state`() = runTest {
//        coEvery { fakeOrderRepo.findByTableId(10L) } returns listOf(sampleOrder)
//        coEvery { fakeProductRepo.getAll() } returns listOf(sampleProduct)
//
//        viewModel.loadOrdersForTable(10L)
//
//        viewModel.orderDisplayUiState.test {
//            val state = awaitItem()
//            assertEquals(1, state.orders.size)
//            assertEquals(4000, state.totalPrice)
//        }
//    }
}