package com.yefim.giphy_api.api

import com.yefim.giphy_api.defaults.GiphySearchApiDefaults.DEFAULT_LIMIT
import com.yefim.giphy_api.defaults.GiphySearchApiDefaults.DEFAULT_OFFSET
import com.yefim.giphy_api.model.GiphySearchResponse
import com.yefim.giphy_api.model.MPAARating
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphySearchApi {
    @GET("search")
    suspend fun search(
        @Query("api_key") key: String,
        @Query("q", encoded = true) query: String,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int = DEFAULT_OFFSET,
        @Query("rating") rating: MPAARating? = null,
    ): GiphySearchResponse
}