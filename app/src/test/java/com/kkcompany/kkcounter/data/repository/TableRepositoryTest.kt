package com.kkcompany.kkcounter.data.repository

import com.kkcompany.kkcounter.data.db.dao.TableDao
import com.kkcompany.kkcounter.data.db.entities.TableEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TableRepositoryTest {

    private val fakeTableDao = mockk<TableDao>(relaxed = true)
    private lateinit var repo: TableRepository

    @Before
    fun setup() {
        repo = TableRepository(fakeTableDao)
    }

    @Test
    fun `insert calls DAO`() = runTest {
        val table = TableEntity(id = 1L, name = "A", xRatio = 0f, yRatio = 0f, widthRatio = 100f, heightRatio = 100f, description = "", floor = 1)
        repo.insert(table)
        coVerify { fakeTableDao.insert(table) }
    }

    @Test
    fun `update calls DAO`() = runTest {
        repo.update(1L, 10f, 20f, 50f, 50f)
        coVerify { fakeTableDao.update(1L, 10f, 20f, 50f, 50f) }
    }

    @Test
    fun `getAll returns DAO result`() = runTest {
        val tables = listOf(TableEntity(id = 1L, name = "A", xRatio = 0f, yRatio = 0f, widthRatio = 100f, heightRatio = 100f, description = "", floor = 1))
        coEvery { fakeTableDao.getAll() } returns tables

        val result = repo.getAll()
        assertEquals(tables, result)
    }
}