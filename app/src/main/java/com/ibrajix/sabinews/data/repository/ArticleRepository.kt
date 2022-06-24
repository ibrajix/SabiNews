package com.ibrajix.sabinews.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ibrajix.sabinews.data.database.main.ArticleDatabase
import com.ibrajix.sabinews.data.database.main.ArticleRemoteMediator
import com.ibrajix.sabinews.data.model.Article
import com.ibrajix.sabinews.data.model.ArticleResponse
import com.ibrajix.sabinews.data.remotepaging.SearchPagingSource
import com.ibrajix.sabinews.network.ApiService
import com.ibrajix.sabinews.network.BaseDataSource
import com.ibrajix.sabinews.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticleRepository @Inject constructor(
    private val apiService: ApiService,
    private val articleDatabase: ArticleDatabase
) : BaseDataSource() {

    fun getAllNews(): Flow<PagingData<Article>> {

        val pagingSourceFactory = {
            articleDatabase.articleDao().getAllArticles()
        }
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = ArticleRemoteMediator(
                apiService, articleDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun searchForNews(q: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchPagingSource(apiService, q)
            }
        ).flow
    }

}