package com.yefim.gifsearcher.screen.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.yefim.gifsearcher.defaults.Defaults.DEBOUNCE_TIME
import com.yefim.gifsearcher.ui.util.GifPagingSource
import com.yefim.giphy_api.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

data class GifSearchScreenState(
    val query: String = "",
    val error: Exception? = null
)

@HiltViewModel
class GifSearchViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {
    private val _gifSearchScreenState = mutableStateOf(GifSearchScreenState())
    val gifSearchScreenState: State<GifSearchScreenState> = _gifSearchScreenState

    var gifFlow = makeGifFlow()

    @OptIn(FlowPreview::class)
    private fun makeGifFlow() = Pager(PagingConfig(pageSize = 1)) {
        GifPagingSource(
            gifService = repository,
            query = _gifSearchScreenState.value.query,
            onLoadError = ::catchError
        )
    }.flow
        .debounce(DEBOUNCE_TIME)
        .cachedIn(viewModelScope)

    fun catchError(e: Exception) {
        _gifSearchScreenState.value = _gifSearchScreenState.value.copy(error = e)
    }

    fun updateSearchQuery(query: String) {
        _gifSearchScreenState.value = _gifSearchScreenState.value.copy(query = query)
        gifFlow = makeGifFlow()
    }
}