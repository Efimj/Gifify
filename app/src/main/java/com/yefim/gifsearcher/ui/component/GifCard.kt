package com.yefim.gifsearcher.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.decode.GifDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun GifCard(
    modifier: Modifier = Modifier,
    gifUrl: String,
    onClick: () -> Unit = {},
    shimmer: Shimmer
) {
    var aspectRatio by rememberSaveable { mutableStateOf(1f) }

    val model = ImageRequest.Builder(LocalContext.current)
        .data(gifUrl)
        .memoryCacheKey(gifUrl)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .decoderFactory(GifDecoder.Factory())
        .build()

    SubcomposeAsyncImage(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .aspectRatio(aspectRatio)
            .clickable { onClick() },
        onSuccess = {
            aspectRatio =
                (it.result.drawable.run { intrinsicWidth / intrinsicHeight.toFloat() })
        },
        model = model,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        loading = {
            GifStub(aspectRatio = aspectRatio, shimmer = shimmer)
        },
    )
}

@Composable
private fun GifStub(aspectRatio: Float, shimmer: Shimmer) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .aspectRatio(aspectRatio)
            .shimmer(shimmer)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
    )
}