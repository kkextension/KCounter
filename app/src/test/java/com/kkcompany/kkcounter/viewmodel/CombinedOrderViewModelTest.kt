package com.kkcompany.kkcounter.viewmodel

import app.cash.turbine.test
import com.kkcompany.kkcounter.data.db.entities.OrderHistoryEntity
import com.kkcompany.kkcounter.data.repository.OrderHistoryRepository
import com.kkcompany.kkcounter.data.repository.OrderRepository
import com.kkcompany.kkcounter.ui.model.CombinedOrderDisplay
import com.kkcompany.kkcounter.ui.model.OrderDisplayItem
import com.kkcompany.kkcounter.utils.CombineOrderMapper
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class CombinedOrderViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CombinedOrderViewModel

    private val fakeOrderRepo = mockk<OrderRepository>(relaxed = true)
    private val fakeHistoryRepo = mockk<OrderHistoryRepository>(relaxed = true)
    private val fakeCombineOrderMapper = mockk<CombineOrderMapper>(relaxed = true)

    @Before
    fun setup() {
        viewModel = CombinedOrderViewModel(fakeOrderRepo, fakeHistoryRepo, fakeCombineOrderMapper)
    }

//    @Test
//    fun `loadAllCombinedOrders updates combinedOrders with mapped result`() = runTest {
//        // Given
//        val currentOrders = listOf(
//            OrderDisplayItem(
//                id = 1L,
//                tableId = 1L,
//                productId = 101L,
//                productName = "맥주",
//                quantity = 2,
//                price = 5000,
//                orderedAt = 1000L
//            )
//        )
//        val histories = listOf(
//            OrderHistoryEntity(
//                id = 1L,
//                tableId = 2L,
//                itemsJson = """[{"productId":102,"productName":"소주","quantity":1,"price":4000}]""",
//                totalPrice = 4000,
//                completedAt = 500L
//            )
//        )
//        val expectedCombined = listOf(
//            CombinedOrderDisplay(
//                tableId = 1L,
//                items = currentOrders,
//                totalPrice = 10000,
//                completedAt = Date(1000L)
//            ),
//            CombinedOrderDisplay(
//                tableId = 2L,
//                items = listOf(
//                    OrderDisplayItem(
//                        id = 0,
//                        tableId = 2L,
//                        productId = 102L,
//                        productName = "소주",
//                        quantity = 1,
//                        price = 4000,
//                        orderedAt = 0L
//                    )
//                ),
//                totalPrice = 4000,
//                completedAt = Date(500L)
//            )
//        )
//
//        coEvery { fakeOrderRepo.getAllOrderDisplayItems() } returns currentOrders
//        coEvery { fakeHistoryRepo.getAll() } returns histories
//        coEvery { fakeCombineOrderMapper.map(currentOrders, histories) } returns expectedCombined
//
//        // When
//        viewModel.loadAllCombinedOrders()
//        advanceUntilIdle()
//
//        // Then
//        viewModel.combinedOrders.test {
//            val result = awaitItem()
//            assertEquals(expectedCombined, result)
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
}