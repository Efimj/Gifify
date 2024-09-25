package com.yefim.gifsearcher.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SignalWifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.yefim.gifsearcher.R
import com.yefim.gifsearcher.ui.component.GifCard
import com.yefim.gifsearcher.ui.component.Stub
import com.yefim.gifsearcher.ui.util.LocalInsetsPaddings
import com.yefim.gifsearcher.ui.util.rememberNetworkAvailable
import com.yefim.giphy_api.model.GIF

@Composable
fun GifDetailScreen(gifId: String, viewModel: GifDetailViewModel = hiltViewModel()) {
    val insets = LocalInsetsPaddings.current
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    val isNetworkAvailable = rememberNetworkAvailable()
    val state = viewModel.gifDetailScreenState.value

    LaunchedEffect(Unit) {
        viewModel.findGif(gifId)
    }

    if (isNetworkAvailable.not()) {
        Stub(
            modifier = Modifier.fillMaxSize(),
            icon = Icons.Outlined.SignalWifiOff,
            title = stringResource(R.string.no_internet),
            description = stringResource(R.string.the_application_requires_internet_to_work)
        )
    } else if (state.loading) {
        Stub(
            modifier = Modifier.fillMaxSize(),
            loadingIndicator = {
                CircularProgressIndicator()
            },
            title = stringResource(R.string.loading),
            description = stringResource(R.string.it_usually_takes_a_couple_of_seconds)
        )
    } else if (state.gif != null) {
        GifDetail(insets, state.gif, shimmer)
    }
}

@Composable
private fun GifDetail(
    insets: PaddingValues,
    gif: GIF,
    shimmer: Shimmer
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(insets)
            .padding(horizontal = 20.dp)
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GifCard(
            modifier = Modifier.fillMaxWidth(),
            gifUrl = gif.images.downsized.url,
            shimmer = shimmer
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            Headline(stringResource(R.string.main))
            RowWrapper {
                CustomTextWithLabelCard(
                    label = stringResource(R.string.id),
                    value = gif.id,
                )
                CustomTextWithLabelCard(
                    label = stringResource(R.string.slug),
                    value = gif.slug,
                )
            }
            Headline(stringResource(R.string.description))
            RowWrapper {
                CustomTextWithLabelCard(
                    label = stringResource(R.string.title),
                    value = gif.title,
                )
                CustomTextWithLabelCard(
                    label = stringResource(R.string.alt_text),
                    value = gif.altText,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextWithLabelCard(
                label = stringResource(R.string.mpaa_rating),
                value = gif.rating,
            )
            Headline(stringResource(R.string.sizes))
            RowWrapper {
                CustomTextWithLabelCard(
                    label = stringResource(R.string.height),
                    value = gif.images.downsized.height,
                )
                CustomTextWithLabelCard(
                    label = stringResource(R.string.width),
                    value = gif.images.downsized.width,
                )
                CustomTextWithLabelCard(
                    label = stringResource(R.string.size),
                    value = gif.images.downsized.size,
                )
            }
            Headline(stringResource(R.string.source))
            CustomTextWithLabelCard(
                label = stringResource(R.string.url),
                value = gif.url,
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextWithLabelCard(
                label = stringResource(R.string.bitly_url),
                value = gif.bitlyUrl,
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextWithLabelCard(
                label = stringResource(R.string.embed_url),
                value = gif.embedUrl,
            )
        }
    }
}

@Composable
private fun Headline(text: String) {
    Text(
        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f)
    )
}

@Composable
private fun RowScope.CustomTextWithLabelCard(label: String, value: String) {
    val clipboardManager = LocalClipboardManager.current

    Box(
        modifier = Modifier
            .weight(1f)
            .clip(MaterialTheme.shapes.small)
            .clickable { clipboardManager.setText(AnnotatedString(value)) }
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(15.dp)
    ) {
        TextWithLabel(
            label = label,
            text = value
        )
    }
}

@Composable
private fun CustomTextWithLabelCard(label: String, value: String) {
    val clipboardManager = LocalClipboardManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .clickable { clipboardManager.setText(AnnotatedString(value)) }
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(15.dp)
    ) {
        TextWithLabel(
            label = label,
            text = value
        )
    }
}

@Composable
private fun TextWithLabel(text: String, label: String) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 2.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RowWrapper(content: @Composable RowScope.() -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        content()
    }
}