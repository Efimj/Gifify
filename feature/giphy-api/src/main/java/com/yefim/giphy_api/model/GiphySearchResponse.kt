package com.yefim.giphy_api.model

import com.google.gson.annotations.SerializedName

data class GiphySearchResponse(
    @SerializedName("data")
    val gifs: List<GIF> = emptyList(),
    @SerializedName("pagination")
    val pagination: PaginationObject = PaginationObject(),
    @SerializedName("meta")
    val meta: MetaObject = MetaObject(),
)