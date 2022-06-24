package com.ibrajix.sabinews.data.database.main

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ibrajix.sabinews.data.model.Article
import com.ibrajix.sabinews.data.model.ArticleRemoteKeys
import com.ibrajix.sabinews.network.ApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val apiService: ApiService,
    private val articleDatabase: ArticleDatabase
) : RemoteMediator<Int, Article>() {

    private val articleDao = articleDatabase.articleDao()
    private val articleRemoteKeysDao = articleDatabase.articleRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {

        return try {
            val currentPage = when(loadType){
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = apiService.getNews(page = currentPage, pageSize = 10)
            val endOfPaginationReached = response.articles.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            articleDatabase.withTransaction {
                if (loadType == LoadType.REFRESH){
                    articleDao.deleteAllArticles()
                    articleRemoteKeysDao.clearRemoteKeys()
                }
                val keys = response.articles.map { article->
                    ArticleRemoteKeys(
                        id = article.url,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                articleDao.saveArticles(response.articles)
                articleRemoteKeysDao.saveRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): ArticleRemoteKeys? {
        return state.anchorPosition?.let { position->
            state.closestItemToPosition(position)?.url?.let { url->
                articleRemoteKeysDao.getRemoteKeys(url)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
            state: PagingState<Int, Article>
    ) : ArticleRemoteKeys? {

        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        } ?.data?.firstOrNull()?.let {article->
            articleRemoteKeysDao.getRemoteKeys(article.url)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Article>
    ): ArticleRemoteKeys? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        } ?.data?.lastOrNull()?.let { article->
            articleRemoteKeysDao.getRemoteKeys(article.url)
        }
    }


}