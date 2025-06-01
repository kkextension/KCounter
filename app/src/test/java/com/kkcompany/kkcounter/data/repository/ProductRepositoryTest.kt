package com.kkcompany.kkcounter.data.repository

import com.kkcompany.kkcounter.data.db.dao.ProductDao
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {

    private val fakeProductDao = mockk<ProductDao>(relaxed = true)
    private lateinit var repo: ProductRepository

    @Before
    fun setup() {
        repo = ProductRepository(fakeProductDao)
    }

    @Test
    fun `insert calls DAO`() = runTest {
        val product = ProductEntity(name = "Coffee", price = 1000, description = "")
        repo.insert(product)
        coVerify { fakeProductDao.insert(product) }
    }

    @Test
    fun `getAll returns DAO result`() = runTest {
        val products = listOf(ProductEntity(name = "Coffee", price = 1000, description = ""))
        coEvery { fakeProductDao.getAll() } returns products

        val result = repo.getAll()
        assertEquals(products, result)
    }
}