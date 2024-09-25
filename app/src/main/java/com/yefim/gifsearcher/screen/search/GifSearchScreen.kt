package com.yefim.gifsearcher.screen.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SignalWifiOff
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.yefim.gifsearcher.R
import com.yefim.gifsearcher.ui.component.GifsGrid
import com.yefim.gifsearcher.ui.component.Stub
import com.yefim.gifsearcher.ui.util.LocalInsetsPaddings
import com.yefim.gifsearcher.ui.util.rememberNetworkAvailable
import com.yefim.gifsearcher.util.ContextUtil.startDetailGifActivity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifSearchScreen(viewModel: GifSearchViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val insets = LocalInsetsPaddings.current
    val state = viewModel.gifSearchScreenState.value

    val isNetworkAvailable = rememberNetworkAvailable()

    Column(
        modifier = Modifier
            .padding(insets)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val gifItems = viewModel.gifFlow.collectAsLazyPagingItems()

        if (isNetworkAvailable.not()) {
            Stub(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.SignalWifiOff,
                title = stringResource(R.string.no_internet),
                description = stringResource(R.string.the_application_requires_internet_to_work)
            )
        } else if (gifItems.itemCount > 0) {
            GifsGrid(
                modifier = Modifier.weight(1f),
                gifs = gifItems,
                onClick = { context.startDetailGifActivity(it.id) }
            )
        } else if (state.error != null) {
            val message = state.error.localizedMessage ?: ""

            Stub(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.Warning,
                title = stringResource(R.string.warning),
                description = message
            )
        } else {
            Stub(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.Search,
                title = stringResource(R.string.gif_search),
                description = stringResource(R.string.gifs_will_be_shown_here)
            )
        }
        SearchBar(
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
            windowInsets = WindowInsets.ime,
            inputField = {
                SearchBarDefaults.InputField(
                    query = state.query,
                    onQueryChange = { viewModel.updateSearchQuery(it) },
                    expanded = false,
                    placeholder = { Text(stringResource(R.string.find_your_gifs)) },
                    leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                    onSearch = { },
                    onExpandedChange = {},
                )
            },
            expanded = false,
            onExpandedChange = {},
            content = { },
        )
    }

    ErrorShower(state = state)
}

@Composable
private fun ErrorShower(state: GifSearchScreenState) {
    val context = LocalContext.current

    LaunchedEffect(state.error) {
        state.error?.let {
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }
}