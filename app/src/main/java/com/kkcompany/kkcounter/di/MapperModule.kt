package com.kkcompany.kkcounter.di

import com.kkcompany.kkcounter.utils.CombineOrderMapper
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Provides
    fun provideCombineOrderMapper(): CombineOrderMapper = CombineOrderMapper()
}