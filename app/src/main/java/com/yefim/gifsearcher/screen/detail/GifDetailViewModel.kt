package com.yefim.gifsearcher.screen.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yefim.gifsearcher.defaults.Defaults.GIPHY_KEY
import com.yefim.giphy_api.model.GIF
import com.yefim.giphy_api.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GifDetailScreenState(
    val gif: GIF? = null,
    val loading: Boolean = true,
    val error: Exception? = null,
)

@HiltViewModel
class GifDetailViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {
    private val _gifDetailScreenState = mutableStateOf(GifDetailScreenState())
    val gifDetailScreenState: State<GifDetailScreenState> = _gifDetailScreenState

    fun findGif(id: String) {
        _gifDetailScreenState.value = _gifDetailScreenState.value.copy(loading = true)

        viewModelScope.launch {
            try {
                val response = repository.findById(key = GIPHY_KEY, gifId = id)
                _gifDetailScreenState.value = _gifDetailScreenState.value.copy(gif = response.gif)
            } catch (e: Exception) {
                val message = e.message.toString()
                Log.e("GifPagingSource", message, e)
                _gifDetailScreenState.value = _gifDetailScreenState.value.copy(error = e)
            } finally {
                _gifDetailScreenState.value =
                    _gifDetailScreenState.value.copy(loading = false)
            }
        }
    }
}