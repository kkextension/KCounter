package com.kkcompany.kkcounter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kkcompany.kkcounter.data.db.entities.TableEntity
import com.kkcompany.kkcounter.data.repository.TableRepository
import com.kkcompany.kkcounter.ui.model.RectangleItem
import com.kkcompany.kkcounter.ui.model.enums.TableScreenMode
import com.kkcompany.kkcounter.ui.state.TableUiState
import com.kkcompany.kkcounter.utils.log.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TableViewModel @Inject constructor(
    private val tableRepository: TableRepository
) : ViewModel() {

    val TAG = javaClass.simpleName

    var uiState by mutableStateOf(TableUiState())
        private set

    var screenMode by mutableStateOf(TableScreenMode.DEFAULT)
        private set

    private val _rectangles = mutableStateListOf<RectangleItem>()
    val rectangles: List<RectangleItem> get() = _rectangles

    var showSnackbar by mutableStateOf(false)
        private set

    private var nextId : Long = -1L
    var selectedTableId by mutableStateOf<Long?>(null)
        private set

    init {
        fetchTables()
    }

    fun addRectangle(rect: RectangleItem) {
        _rectangles.add(rect)
    }

    fun startAddingTable() {
        screenMode = TableScreenMode.ADDING
        val newTable = RectangleItem(
            id = nextId++, name = "", floor = 0,
            xRatio = 0.3f, yRatio = 0.3f, widthRatio = 0.5f, heightRatio = 0.3f
        )
        _rectangles.add(newTable)
        selectedTableId = newTable.id
    }

    fun selectTable(id: Long) {
        AppLogger.d(TAG, "selectTable id : " + id)
        selectedTableId = id
        screenMode = TableScreenMode.SELECTED

        _rectangles.forEachIndexed { index, rect ->
            val updated = rect.copy(isSelected = rect.id == id)
            _rectangles[index] = updated
        }

        val selected = _rectangles.firstOrNull { it.id == id }
        selected?.let {
            updateUiState(name = it.name, floor = it.floor.toString(), description = it.description)
        }
    }

    fun startEditingTable() {
        screenMode = TableScreenMode.EDITING
    }

    fun addTable() {
        val floorInt = uiState.floor.toIntOrNull()
        val rect = _rectangles.firstOrNull { it.name.isEmpty() }

        AppLogger.d(TAG, "uiState.name : " + uiState.name)
        AppLogger.d(TAG, "floorInt : " + floorInt)
        AppLogger.d(TAG, "rect : " + rect)

        if (uiState.name.isNotEmpty() && floorInt != null && rect != null) {
            viewModelScope.launch {
                AppLogger.d(TAG, "addTable() : ")
                AppLogger.d(TAG, "uiState.description : " + uiState.description)
                tableRepository.insert(
                    TableEntity(
                        name = uiState.name,
                        floor = floorInt,
                        xRatio = rect.xRatio,
                        yRatio = rect.yRatio,
                        widthRatio = rect.widthRatio,
                        heightRatio = rect.heightRatio,
                        description = uiState.description
                    )
                )
                fetchTables()
                resetUiState()
                showSnackbar = true
            }
        }
    }

    fun fetchTables() {
        viewModelScope.launch {
            val tableList = tableRepository.getAll()
            tableList.forEach { table ->
                AppLogger.d(TAG, "fetchTables() : " + table.toString())
            }
            _rectangles.clear()
            _rectangles.addAll(tableList.map { table ->
                RectangleItem(
                    id = table.id,
                    name = table.name,
                    floor = table.floor,
                    xRatio = table.xRatio,
                    yRatio = table.yRatio,
                    widthRatio = table.widthRatio,
                    heightRatio = table.heightRatio,
                    description = table.description
                )
            })
        }
    }

    fun deleteSelectedTable() {
        selectedTableId?.let { id ->
            viewModelScope.launch {
                tableRepository.deleteById(id)
                _rectangles.removeAll { it.id == id }
                fetchTables()
                resetUiState()
            }
        }
    }

    fun finishAddingTable() {
        resetUiState()
        showSnackbar = true
    }

    fun updateRectangle(id: Long, xRatio: Float, yRatio: Float, widthRatio: Float, heightRatio: Float) {
        val index = _rectangles.indexOfFirst { it.id == id }
        if (index != -1) {
            _rectangles[index] = _rectangles[index].copy(
                xRatio = xRatio,
                yRatio = yRatio,
                widthRatio = widthRatio,
                heightRatio = heightRatio
            )
        }

        viewModelScope.launch {
            tableRepository.update(id, xRatio, yRatio, widthRatio, heightRatio)
        }
    }

    fun updateUiState(name: String? = null, floor: String? = null, description: String? = null) {
        uiState = uiState.copy(
            name = name ?: uiState.name,
            floor = floor ?: uiState.floor,
            description = description ?: uiState.description
        )
    }

    fun getSelectedTable(): RectangleItem? =
        selectedTableId?.let { id -> _rectangles.firstOrNull { it.id == id } }

    fun snackbarShown() {
        showSnackbar = false
    }

    private fun resetUiState() {
        uiState = TableUiState()
        screenMode = TableScreenMode.DEFAULT
        selectedTableId = null
    }

    fun updateRectangleItem(item: RectangleItem) {
        val index = _rectangles.indexOfFirst { it.id == item.id }
        if (index != -1) _rectangles[index] = item
    }
}