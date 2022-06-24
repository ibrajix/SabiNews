package com.ibrajix.sabinews.screens.home.model

import androidx.paging.PagingData
import com.ibrajix.sabinews.data.model.Article
import com.ibrajix.sabinews.data.model.ArticleResponse
import kotlinx.coroutines.flow.Flow

data class ArticleResponseState(
    val isLoading: Boolean = false,
    val result: Flow<PagingData<Article>>? = null
)