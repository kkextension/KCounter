package com.kkcompany.kkcounter.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kkcompany.kkcounter.data.db.entities.OrderEntity

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: OrderEntity)

    @Query("SELECT * FROM order_info")
    suspend fun getAll(): List<OrderEntity>

    @Query("SELECT * FROM order_info WHERE tableId = :tableId")
    suspend fun getByTableId(tableId: Long): List<OrderEntity>

    @Query("SELECT * FROM order_info WHERE tableId = :tableId AND productId = :productId LIMIT 1")
    suspend fun getByTableIdAndProductId(tableId: Long, productId: Long): OrderEntity?

    @Delete
    suspend fun delete(order: OrderEntity)

    @Query("DELETE FROM order_info WHERE tableId = :tableId")
    suspend fun deleteByTableId(tableId: Long)

    @Query("UPDATE order_info SET quantity = :newQuantity WHERE id = :orderId")
    suspend fun updateQuantityById(orderId: Long, newQuantity: Int)
}