package com.ibrajix.sabinews.datastore

import kotlinx.coroutines.flow.Flow

interface StorageInterface {
    fun getSelectedTheme() : Flow<Boolean>
    suspend fun setSelectedTheme(theme: Boolean)
}