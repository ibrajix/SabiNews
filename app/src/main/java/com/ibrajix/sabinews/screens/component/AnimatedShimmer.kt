package com.ibrajix.sabinews.screens.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedShimmer() {

    val shimmerColor = listOf(
        Color.LightGray.copy(alpha = 0.6F),
        Color.LightGray.copy(alpha = 0.2F),
        Color.LightGray.copy(alpha = 0.6F)
    )

    val transition = rememberInfiniteTransition()

    val translateAnimation = transition.animateFloat(
        initialValue = 0F,
        targetValue = 1000F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColor,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )

    ShimmerGridItem(brush = brush)

}

@Composable
fun ShimmerGridItem(brush: Brush){

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ){
        Spacer(
           modifier = Modifier
               .fillMaxWidth()
               .height(200.dp)
               .clip(RectangleShape)
               .background(brush))

        Spacer(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .width(200.dp)
                .height(20.dp)
                .clip(RectangleShape)
                .background(brush))
    }
}

@Composable
@Preview(showBackground = true)
fun ShimmerPreview(){
    ShimmerGridItem(
        brush = Brush.linearGradient(
            listOf(
                Color.LightGray.copy(alpha = 0.6F),
                Color.LightGray.copy(alpha = 0.2F),
                Color.LightGray.copy(alpha = 0.6F)
            )
        )
    )
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun ShimmerPreviewDark(){
    ShimmerGridItem(
        brush = Brush.linearGradient(
            listOf(
                Color.LightGray.copy(alpha = 0.6F),
                Color.LightGray.copy(alpha = 0.2F),
                Color.LightGray.copy(alpha = 0.6F)
            )
        )
    )
}