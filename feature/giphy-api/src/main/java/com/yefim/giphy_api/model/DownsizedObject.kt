package com.yefim.giphy_api.model

import com.google.gson.annotations.SerializedName

/**
 * Data on a version of this GIF downsized to be under 2mb
 *
 * @property url The publicly-accessible direct URL for this GIF
 * @property width The width of this GIF in pixels
 * @property height The height of this GIF in pixels
 * @property size The size of this GIF in bytes
 */
data class DownsizedObject(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("size")
    val size: String,
)