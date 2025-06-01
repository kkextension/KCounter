package com.kkcompany.kkcounter.data.repository

import com.kkcompany.kkcounter.data.db.dao.OrderDao
import com.kkcompany.kkcounter.data.db.dao.ProductDao
import com.kkcompany.kkcounter.data.db.entities.OrderEntity
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderRepositoryTest {

    private val fakeOrderDao = mockk<OrderDao>(relaxed = true)
    private val fakeProductDao = mockk<ProductDao>(relaxed = true)
    private lateinit var repo: OrderRepository

//    @Before
//    fun setup() {
//        repo = OrderRepository(fakeOrderDao, fakeProductDao)
//    }
//
//    @Test
//    fun `insert calls DAO correctly`() = runTest {
//        val order = OrderEntity(tableId = 1L, productId = 1L, quantity = 1)
//        repo.insert(order)
//        coVerify { fakeOrderDao.insert(order) }
//    }
//
//    @Test
//    fun `getAllOrderDisplayItems maps correctly`() = runTest {
//        val orders = listOf(OrderEntity(1L, 1L, 1L, 2))
//        val products = listOf(ProductEntity(1L, "Beer", 3000, "desc"))
//        coEvery { fakeOrderDao.getAll() } returns orders
//        coEvery { fakeProductDao.getAll() } returns products
//
//        val result = repo.getAllOrderDisplayItems()
//
//        assertEquals(1, result.size)
//        assertEquals("Beer", result.first().productName)
//    }

    // etc... (findByTableId, deleteByTableId 테스트)
}