package com.yefim.giphy_api.model

import com.google.gson.annotations.SerializedName

/**
 * Data on versions of this GIF with a fixed height of 200 pixels. Good for mobile use
 * @property url The publicly-accessible direct URL for this GIF
 * @property width The width of this GIF in pixels
 * @property height The height of this GIF in pixels
 * @property size The size of this GIF in bytes
 */
data class FixedHeightObject(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("size")
    val size: String,
)