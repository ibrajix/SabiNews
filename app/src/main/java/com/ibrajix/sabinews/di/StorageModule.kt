package com.ibrajix.sabinews.di

import com.ibrajix.sabinews.datastore.Storage
import com.ibrajix.sabinews.datastore.StorageInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun bindDataStorage(storage: Storage): StorageInterface

}