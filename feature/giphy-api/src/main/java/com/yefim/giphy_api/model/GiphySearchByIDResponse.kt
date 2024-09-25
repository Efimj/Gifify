package com.yefim.giphy_api.model

import com.google.gson.annotations.SerializedName

data class GiphySearchByIDResponse(
    @SerializedName("data")
    val gif: GIF? = null,
    @SerializedName("meta")
    val meta: MetaObject = MetaObject(),
)
