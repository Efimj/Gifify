package com.yefim.gifsearcher.ui.util

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yefim.gifsearcher.defaults.Defaults
import com.yefim.gifsearcher.defaults.Defaults.MAX_GIFS_COUNT_PER_FETCH
import com.yefim.giphy_api.model.GIF
import com.yefim.giphy_api.repository.GiphyRepository

class GifPagingSource(
    val gifService: GiphyRepository,
    val query: String,
    val onLoadError: (Exception) -> Unit
) : PagingSource<Int, GIF>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, GIF> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = gifService.search(
                key = Defaults.GIPHY_KEY,
                query = query,
                limit = MAX_GIFS_COUNT_PER_FETCH,
                offset = nextPageNumber * MAX_GIFS_COUNT_PER_FETCH,
            )

            val nextKey = if (response.gifs.isNotEmpty()) nextPageNumber.inc() else null

            return LoadResult.Page(
                data = response.gifs,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            val message = e.message.toString()
            Log.e("GifPagingSource", message, e)
            onLoadError(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GIF>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}