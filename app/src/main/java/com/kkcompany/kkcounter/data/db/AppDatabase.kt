package com.kkcompany.kkcounter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kkcompany.kkcounter.data.db.dao.OrderDao
import com.kkcompany.kkcounter.data.db.dao.OrderHistoryDao
import com.kkcompany.kkcounter.data.db.dao.ProductDao
import com.kkcompany.kkcounter.data.db.dao.TableDao
import com.kkcompany.kkcounter.data.db.entities.OrderEntity
import com.kkcompany.kkcounter.data.db.entities.OrderHistoryEntity
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import com.kkcompany.kkcounter.data.db.entities.TableEntity

@Database(entities = [ProductEntity::class, TableEntity::class, OrderEntity::class, OrderHistoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun tableDao(): TableDao
    abstract fun orderDao(): OrderDao
    abstract fun orderHistoryDao(): OrderHistoryDao
}