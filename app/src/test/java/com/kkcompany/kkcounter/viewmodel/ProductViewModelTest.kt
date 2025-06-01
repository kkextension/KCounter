package com.kkcompany.kkcounter.viewmodel

import app.cash.turbine.test
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import com.kkcompany.kkcounter.data.repository.ProductRepository
import com.kkcompany.kkcounter.ui.model.enums.SortColumn
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
class ProductViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule() // 코루틴 테스트 환경 구성

    private lateinit var viewModel: ProductViewModel
    private val fakeRepo = mockk<ProductRepository>(relaxed = true)

    @Before
    fun setup() {
        viewModel = ProductViewModel(fakeRepo)
    }

    @Test
    fun `addProduct - valid input - calls insert and fetchProducts`() = runTest {
        // given
        viewModel.updateUiState(name = "Coffee", price = "5000", description = "Hot coffee")
        coEvery { fakeRepo.insert(any()) } just Runs
        coEvery { fakeRepo.getAll() } returns emptyList()

        // when
        viewModel.addProduct()

        // then
        coVerify { fakeRepo.insert(match { it.name == "Coffee" && it.price == 5000 }) }
        coVerify { fakeRepo.getAll() }
        assertEquals("", viewModel.uiState.name)
        assertEquals(true, viewModel.showSnackbar)
    }

    @Test
    fun `addProduct - invalid input - does not call insert`() = runTest {
        viewModel.updateUiState(name = "", price = "abc", description = "Invalid")

        viewModel.addProduct()

        coVerify(exactly = 0) { fakeRepo.insert(any()) }
    }

    @Test
    fun `fetchProducts - gets from repository and updates flow`() = runTest {
        val products = listOf(
            ProductEntity(name = "Coffee", price = 5000, description = "Hot"),
            ProductEntity(name = "Tea", price = 4000, description = "Green")
        )
        coEvery { fakeRepo.getAll() } returns products

        // when fetching products
        viewModel.fetchProducts()

        // then check if the products are correctly fetched
        viewModel.products.test {
            val result = awaitItem()
            assertEquals(2, result.size)
            assertEquals("Coffee", result[0].name)
        }
    }

    @Test
    fun `sortProducts - changes order according to sortColumn and order`() = runTest {
        val products = listOf(
            ProductEntity(0L, "A", 1000, "descA"),
            ProductEntity(1L, "B", 2000, "descB")
        )
        coEvery { fakeRepo.getAll() } returns products
        viewModel.fetchProducts()

        // when sorting products by name in ascending order
        viewModel.sortProducts(SortColumn.NAME, true)

        // then verify sorted order
        viewModel.products.test {
            val sorted = awaitItem()
            assertEquals("A", sorted[0].name)
            assertEquals("B", sorted[1].name)
        }
    }

    @Test
    fun `sortProducts - handles empty list gracefully`() = runTest {
        // given an empty product list
        coEvery { fakeRepo.getAll() } returns emptyList()
        viewModel.fetchProducts()

        // when sorting an empty list
        viewModel.sortProducts(SortColumn.NAME, true)

        // then ensure no IndexOutOfBoundsException is thrown
        viewModel.products.test {
            val result = awaitItem()
            assertEquals(0, result.size)  // Ensure the list is still empty
        }
    }

    @Test
    fun `sortProducts - ensures sorting logic works on non-empty list`() = runTest {
        // given a non-empty product list
        val products = listOf(
            ProductEntity(1L, "A", 2000, "descA"),
            ProductEntity(0L, "B", 1000, "descB")
        )
        coEvery { fakeRepo.getAll() } returns products
        viewModel.fetchProducts()

        // when sorting products
        viewModel.sortProducts(SortColumn.NAME, true)

        // then verify sorted order
        viewModel.products.test {
            val sorted = awaitItem()
            assertEquals("A", sorted[0].name)
            assertEquals("B", sorted[1].name)
        }
    }

    @Test
    fun `snackbarShown - resets showSnackbar to false`() {
        viewModel.snackbarShown()
        assertEquals(false, viewModel.showSnackbar)
    }
}