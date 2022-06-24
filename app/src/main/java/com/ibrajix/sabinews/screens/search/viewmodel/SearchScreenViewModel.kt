package com.ibrajix.sabinews.screens.search.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ibrajix.sabinews.data.model.Article
import com.ibrajix.sabinews.data.model.ArticleResponse
import com.ibrajix.sabinews.data.repository.ArticleRepository
import com.ibrajix.sabinews.network.Resource
import com.ibrajix.sabinews.screens.home.model.ArticleResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val articleRepository: ArticleRepository) : ViewModel() {


    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchTextState(newValue: String){
        _searchTextState.value = newValue
    }


    //get searched text
    private val _searchNews = MutableStateFlow(ArticleResponseState())
    val searchNews : StateFlow<ArticleResponseState> = _searchNews.asStateFlow()

    fun doSearchNews(q: String){
        viewModelScope.launch {
            try {
                _searchNews.update { it.copy(isLoading = true) }
                val getResponse = articleRepository.searchForNews(q)
                _searchNews.update {
                    it.copy(
                        isLoading = false,
                        result = getResponse
                    )
                }
            } catch (e: IOException){
                //handle error
            }
        }
    }

}