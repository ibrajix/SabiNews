package com.ibrajix.sabinews.data.remotepaging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ibrajix.sabinews.data.model.Article
import com.ibrajix.sabinews.network.ApiService

class SearchPagingSource(private val apiService: ApiService, private val q: String)
    : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val currentPage = params.key ?: 1

        return try {
            val response = apiService.searchForNews(q = q)
            val endOfPaginationReached = response.articles.isEmpty()
            if (response.articles.isNotEmpty()){
                LoadResult.Page(
                    data = response.articles,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
              LoadResult.Page(
                  data = emptyList(),
                  prevKey = null,
                  nextKey = null
              )
            }
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

}