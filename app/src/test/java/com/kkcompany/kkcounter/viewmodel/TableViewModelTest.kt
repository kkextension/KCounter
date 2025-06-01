package com.kkcompany.kkcounter.viewmodel

import com.kkcompany.kkcounter.data.repository.TableRepository
import com.kkcompany.kkcounter.ui.model.RectangleItem
import com.kkcompany.kkcounter.ui.model.enums.TableScreenMode
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TableViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule() // coroutine 테스트 환경 구성

    private lateinit var viewModel: TableViewModel
    private val repository: TableRepository = mockk(relaxed = true) // relaxed: 모든 함수 자동 stub

    @Before
    fun setup() {
        viewModel = TableViewModel(repository)
    }

    @Test
    fun `startAddingTable should add rectangle with default values and switch mode`() {
        // when
        viewModel.startAddingTable()

        // then
        assertEquals(TableScreenMode.ADDING, viewModel.screenMode)
        val added = viewModel.rectangles.last()
        assertEquals("", added.name)
        assertEquals(0.2f, added.xRatio)
    }

//    @Test
//    fun `addTable should insert new table when inputs are valid`() = runTest {
//        // given
//        viewModel.startAddingTable()
//        viewModel.updateUiState(name = "테이블1", floor = "1")
//
//        coEvery { repository.insert(any()) } just Runs
//
//        // when
//        viewModel.addTable()
//
//        // then
//        coVerify { repository.insert(match {
//            it.name == "테이블1" && it.floor == 1
//        }) }
//        assertEquals(TableScreenMode.DEFAULT, viewModel.screenMode)
//        assertTrue(viewModel.showSnackbar)
//    }

    @Test
    fun `deleteSelectedTable should remove rectangle and reset mode`() {
        // given
        val rect = RectangleItem(id = 1L, name = "A", floor = 1, xRatio = 0.1f, yRatio = 0.1f, widthRatio = 0.2f, heightRatio = 0.2f)
        viewModel.startAddingTable()
        viewModel.selectTable(rect.id)
        viewModel.updateRectangleItem(rect)

        // when
        viewModel.deleteSelectedTable()

        // then
        assertEquals(TableScreenMode.DEFAULT, viewModel.screenMode)
        assertNull(viewModel.getSelectedTable())
    }

    @Test
    fun `updateRectangle should change values and call repository`() = runTest {
        // given
        val rect = RectangleItem(id = 1L, name = "B", floor = 1, xRatio = 0.1f, yRatio = 0.1f, widthRatio = 0.2f, heightRatio = 0.2f)

        viewModel.updateRectangleItem(rect) // 이 메서드가 내부에서 추가 기능을 안 한다면 아래 코드 필요
        viewModel.addRectangle(rect) // <-- 명시적으로 추가

        // ViewModel 내부 리스트에 수동으로 삽입
        viewModel.updateRectangleItem(rect)

        // when
        viewModel.updateRectangle(1L, 0.3f, 0.3f, 0.4f, 0.4f)

        // then
        val updated = viewModel.rectangles.find { it.id == 1L }
        assertNotNull(updated)
        assertEquals(0.3f, updated!!.xRatio)
        coVerify { repository.update(1L, 0.3f, 0.3f, 0.4f, 0.4f) }
    }
}