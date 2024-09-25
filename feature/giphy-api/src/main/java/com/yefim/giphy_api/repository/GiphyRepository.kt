package com.yefim.giphy_api.repository

import com.yefim.giphy_api.api.GiphySearchApi
import com.yefim.giphy_api.api.GiphySearchByIDApi
import com.yefim.giphy_api.defaults.GiphySearchApiDefaults.DEFAULT_RATING
import com.yefim.giphy_api.model.GiphySearchByIDResponse
import com.yefim.giphy_api.model.GiphySearchResponse
import com.yefim.giphy_api.model.MPAARating
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepository @Inject constructor(
    private val searchService: GiphySearchApi,
    private val findByIdService: GiphySearchByIDApi
) {
    suspend fun search(
        key: String,
        query: String,
        limit: Int,
        offset: Int,
        rating: MPAARating = DEFAULT_RATING
    ): GiphySearchResponse {
        return searchService.search(
            key = key,
            query = query,
            limit = limit,
            offset = offset,
            rating = rating
        )
    }

    suspend fun findById(
        key: String,
        gifId: String,
        rating: MPAARating? = null
    ): GiphySearchByIDResponse {
        return findByIdService.findByID(
            key = key,
            id = gifId,
            rating = rating
        )
    }
}