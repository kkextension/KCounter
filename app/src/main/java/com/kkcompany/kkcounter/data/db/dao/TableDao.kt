package com.kkcompany.kkcounter.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kkcompany.kkcounter.data.db.entities.TableEntity

@Dao
interface TableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(table: TableEntity): Long

    @Query("SELECT * FROM table_info")
    suspend fun getAll(): List<TableEntity>

    @Query("UPDATE table_info SET xRatio = :x, yRatio = :y, widthRatio = :width, heightRatio = :height WHERE id = :id")
    suspend fun update(id: Long, x: Float, y: Float, width: Float, height: Float)

    @Query("DELETE FROM table_info WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM table_info WHERE id = :tableId")
    suspend fun getByTableId(tableId: Long): TableEntity
}