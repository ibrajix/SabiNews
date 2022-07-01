package com.ibrajix.sabinews.screens.component

import android.annotation.SuppressLint
import com.ibrajix.sabinews.screens.detail.ShowCustomChromeTab
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.ibrajix.sabinews.R
import com.ibrajix.sabinews.data.model.Article
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ListNewsContent(items: LazyPagingItems<Article>){

    var shouldOpenChromeTab by rememberSaveable { mutableStateOf(false) }
    var articleUrl by rememberSaveable { mutableStateOf("") }


    if (shouldOpenChromeTab){
        ShowCustomChromeTab(articleUrl)
        shouldOpenChromeTab = !shouldOpenChromeTab
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        items(
            items = items,
            key = { article->
                article.url
            }
        ){ article->
            article?.let { ArticleItem(it,
                onClick = { url->
                    shouldOpenChromeTab = true
                    articleUrl = url ?: "https://google.com"
                }
            )}
        }
    }

}

@Composable
fun ArticleItem(article: Article, onClick: (String?) -> Unit){

    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .clickable {
                onClick(article.url)
            },
        contentAlignment = Alignment.BottomCenter
    ){

        GlideImage(
            modifier = Modifier.fillMaxSize(),
            imageModel = article.urlToImage,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.icon)
        )

        Surface(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .alpha(ContentAlpha.medium),
            color = Color.Black
        ) {

        }

        article.title?.let {
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp, start = 20.dp, end = 20.dp),
                text = it,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle2,
                color = Color.White,
                overflow = TextOverflow.Ellipsis
            )
        }


    }
}

