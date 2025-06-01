package com.kkcompany.kkcounter.data.repository

import com.kkcompany.kkcounter.data.db.dao.OrderHistoryDao
import com.kkcompany.kkcounter.data.db.entities.OrderHistoryEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class OrderHistoryRepositoryTest {

    private val fakeHistoryDao = mockk<OrderHistoryDao>(relaxed = true)
    private lateinit var repo: OrderHistoryRepository

    @Before
    fun setup() {
        repo = OrderHistoryRepository(fakeHistoryDao)
    }

    @Test
    fun `insert calls DAO`() = runTest {
        val history = OrderHistoryEntity(tableId = 1L, itemsJson = "[]", totalPrice = 0)
        repo.insert(history)
        coVerify { fakeHistoryDao.insert(history) }
    }

    @Test
    fun `getAll returns DAO result`() = runTest {
        val histories = listOf(OrderHistoryEntity(tableId = 1L, itemsJson = "[]", totalPrice = 0))
        coEvery { fakeHistoryDao.getAll() } returns histories

        val result = repo.getAll()
        assertEquals(histories, result)
    }
}