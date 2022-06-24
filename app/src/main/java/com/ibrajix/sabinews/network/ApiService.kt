package com.ibrajix.sabinews.network

import com.ibrajix.sabinews.data.model.Article
import com.ibrajix.sabinews.data.model.ArticleResponse
import com.ibrajix.sabinews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int
    ) : ArticleResponse

    //search for news
    @GET("everything")
    suspend fun searchForNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String =  Constants.API_KEY,
    ): ArticleResponse

}
