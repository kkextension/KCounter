package com.kkcompany.kkcounter.data.repository

import com.kkcompany.kkcounter.data.db.dao.ProductDao
import com.kkcompany.kkcounter.data.db.entities.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor (private val productDao: ProductDao) {
    suspend fun insert(product: ProductEntity) {
        productDao.insert(product)
    }

    suspend fun getAll(): List<ProductEntity> {
        return productDao.getAll()
    }
}