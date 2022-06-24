package com.ibrajix.sabinews.data.database.main

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ibrajix.sabinews.data.database.dao.ArticleDao
import com.ibrajix.sabinews.data.database.dao.ArticleRemoteKeysDao
import com.ibrajix.sabinews.data.model.Article
import com.ibrajix.sabinews.data.model.ArticleRemoteKeys

@Database(entities = [Article::class, ArticleRemoteKeys::class], version = 1)
abstract class ArticleDatabase  : RoomDatabase() {

    abstract fun articleDao() : ArticleDao
    abstract fun articleRemoteKeysDao() : ArticleRemoteKeysDao

}