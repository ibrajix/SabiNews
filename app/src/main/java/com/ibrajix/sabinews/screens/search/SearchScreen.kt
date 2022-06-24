package com.ibrajix.sabinews.screens.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ibrajix.sabinews.R
import com.ibrajix.sabinews.screens.component.AnimatedShimmer
import com.ibrajix.sabinews.screens.component.ListNewsContent
import com.ibrajix.sabinews.screens.component.SearchWidget
import com.ibrajix.sabinews.screens.home.model.ArticleResponseState
import com.ibrajix.sabinews.screens.search.viewmodel.SearchScreenViewModel
import com.ibrajix.sabinews.style.theme.textFieldOutlineColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel(),
){

    val searchTextState by searchScreenViewModel.searchTextState
    /*val getSearchResults = searchScreenViewModel.searchNews.collectAsLazyPagingItems()*/
    val uiState by searchScreenViewModel.searchNews.collectAsState(initial = ArticleResponseState(isLoading = true))
    var job: Job? = null

    Scaffold(
        topBar = {
            SearchWidget(
                navigator = navigator,
                text = searchTextState,
                onTextChange = {
                     searchScreenViewModel.updateSearchTextState(it)

                    //do search without clicking the search icon
                    job?.cancel()
                    job = MainScope().launch {
                        delay(100L)
                    }
                    if (it.isNotEmpty()){
                        searchScreenViewModel.doSearchNews(it)
                    }
                },
                onCloseClicked = {
                    searchScreenViewModel.updateSearchTextState("")
                },
                onSearchClicked = {
                    //do search here
                    searchScreenViewModel.doSearchNews(it)
                }
            )
        },
        content = {

           when(uiState){
                ArticleResponseState(isLoading = true) -> {
                    //show spinner
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 50.dp)
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            uiState.result?.let {
                ListNewsContent(
                    items = it.collectAsLazyPagingItems())
            }

        }
    )

}
