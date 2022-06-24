package com.ibrajix.sabinews.network

import com.google.gson.Gson
import com.ibrajix.sabinews.data.model.ArticleResponse
import retrofit2.Response

abstract class BaseDataSource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {

        try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Resource.success(body)
                }
            }

            else{
                val message: ArticleResponse = Gson().fromJson(response.errorBody()!!.charStream(), ArticleResponse::class.java)
                return Resource.error(message.status)
            }

            return Resource.failed("Something went wrong, try again later")

        } catch (e: Exception) {
            return Resource.failed("Something went wrong")
        }
    }

}