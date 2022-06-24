package com.ibrajix.sabinews.screens.home.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrajix.sabinews.data.repository.ArticleRepository
import com.ibrajix.sabinews.screens.home.model.ArticleResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _getNews = MutableStateFlow(ArticleResponseState())
    val getNews: StateFlow<ArticleResponseState> = _getNews.asStateFlow()

    private var gatNewsJob: Job? = null

    fun doGetNews(){
        if (gatNewsJob != null) return
        gatNewsJob = viewModelScope.launch {
            try {
                _getNews.update { it.copy(isLoading = true) }
                val getResponse = articleRepository.getAllNews()
                _getNews.update {
                    it.copy(
                        isLoading = false,
                        result = getResponse
                    )
                }
            } catch (e: IOException){
                //handle error
            }
            finally {
                gatNewsJob = null
            }
        }
    }

}