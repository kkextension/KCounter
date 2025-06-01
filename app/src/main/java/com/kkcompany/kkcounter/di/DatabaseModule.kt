package com.kkcompany.kkcounter.di

import android.content.Context
import androidx.room.Room
import com.kkcompany.kkcounter.KKCounterApp
import com.kkcompany.kkcounter.data.db.AppDatabase
import com.kkcompany.kkcounter.data.db.dao.OrderDao
import com.kkcompany.kkcounter.data.db.dao.OrderHistoryDao
import com.kkcompany.kkcounter.data.db.dao.ProductDao
import com.kkcompany.kkcounter.data.db.dao.TableDao
import com.kkcompany.kkcounter.data.repository.OrderHistoryRepository
import com.kkcompany.kkcounter.data.repository.OrderRepository
import com.kkcompany.kkcounter.data.repository.ProductRepository
import com.kkcompany.kkcounter.data.repository.TableRepository
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideContext(@ApplicationContext app: KKCounterApp): Context {
        return app.applicationContext
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideTableDao(database: AppDatabase): TableDao {
        return database.tableDao()
    }

    @Provides
    fun provideOrderDao(database: AppDatabase): OrderDao {
        return database.orderDao()
    }

    @Provides
    fun provideOrderHistoryDao(database: AppDatabase): OrderHistoryDao {
        return database.orderHistoryDao()
    }

    @Singleton
    @Provides
    fun provideProductRepository(productDao: ProductDao): ProductRepository {
        return ProductRepository(productDao)
    }

    @Singleton
    @Provides
    fun provideTableRepository(tableDao: TableDao): TableRepository {
        return TableRepository(tableDao)
    }

    @Singleton
    @Provides
    fun provideOrderRepository(orderDao: OrderDao, productDao: ProductDao, tableRepository: TableRepository): OrderRepository {
        return OrderRepository(orderDao, productDao, tableRepository)
    }

    @Singleton
    @Provides
    fun provideOrderHistoryRepository(orderHistoryDao: OrderHistoryDao): OrderHistoryRepository {
        return OrderHistoryRepository(orderHistoryDao)
    }
}