package com.yefim.giphy_api.api

import com.yefim.giphy_api.model.GiphySearchByIDResponse
import com.yefim.giphy_api.model.MPAARating
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiphySearchByIDApi {
    @GET("{gif_id}")
    suspend fun findByID(
        @Path("gif_id") id: String,
        @Query("api_key") key: String,
        @Query("random_id") randomId: Int? = null,
        @Query("rating") rating: MPAARating? = null
    ): GiphySearchByIDResponse
}