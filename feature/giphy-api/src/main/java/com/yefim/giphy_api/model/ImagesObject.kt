package com.yefim.giphy_api.model

import com.google.gson.annotations.SerializedName

/**
 * The Images Object found in the GIF Object contains a series of Rendition Objects
 *
 * @property fixedHeight Data on versions of this GIF with a fixed height of 200 pixels. Good for mobile use
 * @property downsized Data on a version of this GIF downsized to be under 2mb
 */
data class ImagesObject(
    @SerializedName("fixed_height")
    val fixedHeight: FixedHeightObject,
    @SerializedName("downsized")
    val downsized: DownsizedObject
)