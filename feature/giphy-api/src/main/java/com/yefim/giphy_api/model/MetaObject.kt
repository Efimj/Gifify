package com.yefim.giphy_api.model

import com.google.gson.annotations.SerializedName

data class MetaObject(
    @SerializedName("msg")
    val msg: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("response_id")
    val responseId: String = "",
)
