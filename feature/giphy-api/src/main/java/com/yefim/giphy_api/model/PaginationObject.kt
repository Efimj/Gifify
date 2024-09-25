package com.yefim.giphy_api.model

import com.google.gson.annotations.SerializedName

data class PaginationObject(
    @SerializedName("offset")
    val offset: Int = 0,
    @SerializedName("total_count")
    val total: Int = 0,
    @SerializedName("count")
    val count: Int = 0,
)
