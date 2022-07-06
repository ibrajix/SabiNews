package com.ibrajix.sabinews.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.ibrajix.sabinews.R
import com.ibrajix.sabinews.datastore.StorageViewModel
import com.ibrajix.sabinews.destinations.SearchScreenDestination
import com.ibrajix.sabinews.screens.component.AnimatedShimmer
import com.ibrajix.sabinews.screens.component.ListNewsContent
import com.ibrajix.sabinews.screens.home.model.ArticleResponseState
import com.ibrajix.sabinews.screens.home.viewmodel.HomeViewModel
import com.ibrajix.sabinews.style.theme.Shapes
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {

    Scaffold(
        topBar = { AppBar(navigator = navigator) },
        content = {
           DisplayNewsItems()
        }
    )

}


@Composable
fun DisplayNewsItems(homeViewModel: HomeViewModel = hiltViewModel()){

    //get news
    homeViewModel.doGetNews()
    val uiState by homeViewModel.getNews.collectAsState(initial = ArticleResponseState(isLoading = true))

   when(uiState){
        ArticleResponseState(isLoading = true) -> {
            //show shimmer layout or spinner
            val scrollState = rememberScrollState()
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                repeat(7){
                    AnimatedShimmer()
                }
            }
        }
    }

    uiState.result?.let {
        ListNewsContent(
        items = it.collectAsLazyPagingItems())
    }

}


@Composable
fun AppBar(navigator: DestinationsNavigator, storageViewModel: StorageViewModel = hiltViewModel()) {

    val darkModeState = storageViewModel.selectedTheme.observeAsState()

    val drawable = if (darkModeState.value == true){
         R.drawable.ic_moon
    }
    else {
        R.drawable.ic_sun
    }

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.search_news),
                fontSize = 18.sp
            )},
        backgroundColor = MaterialTheme.colors.surface,
        actions = {
            IconButton(onClick = {
                //change the state
                storageViewModel.changeSelectedTheme(isDarkMode = !darkModeState.value!!)
            }) {
                Icon(painter = painterResource(id = drawable), contentDescription = stringResource(id = R.string.icon))
            }
        },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 10.dp)
            .shadow(elevation = 3.dp, shape = Shapes.medium)
            .clickable(
                onClick = {
                   navigator.navigate(SearchScreenDestination)
                }
            )
    )

}