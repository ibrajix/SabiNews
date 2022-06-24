package com.ibrajix.sabinews.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ibrajix.sabinews.data.model.ArticleRemoteKeys

@Dao
interface ArticleRemoteKeysDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveRemoteKeys(remoteKeys: List<ArticleRemoteKeys>)

    @Query("SELECT * FROM article_remote_keys WHERE id = :id ORDER BY id DESC")
    suspend fun getRemoteKeys(id: String): ArticleRemoteKeys

    @Query("DELETE FROM article_remote_keys")
    suspend fun clearRemoteKeys()

}