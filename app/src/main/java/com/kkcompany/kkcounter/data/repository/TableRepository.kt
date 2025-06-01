package com.kkcompany.kkcounter.data.repository

import com.kkcompany.kkcounter.data.db.dao.TableDao
import com.kkcompany.kkcounter.data.db.entities.TableEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableRepository @Inject constructor(private val tableDao: TableDao) {
    suspend fun insert(table: TableEntity): Long {
        return tableDao.insert(table)
    }

    suspend fun getAll(): List<TableEntity> {
        return tableDao.getAll()
    }

    suspend fun update(id: Long, x: Float, y: Float, width: Float, height: Float) {
        return tableDao.update(id, x, y, width, height)
    }

    suspend fun deleteById(id: Long) {
        tableDao.deleteById(id)
    }

    suspend fun findByTableId(tableId: Long): TableEntity {
        return tableDao.getByTableId(tableId)
    }
}