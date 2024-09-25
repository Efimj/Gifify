package com.yefim.giphy_api.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

/**
 * Represents a GIF object
 *
 * @property id This GIF's unique ID
 * @property slug The unique slug used in this GIF's URL
 * @property url The unique URL for this GIF
 * @property bitlyUrl The unique bit.ly URL for this GIF
 * @property embedUrl A URL used for embedding this GIF
 * @property source The page on which this GIF was found
 * @property rating The MPAA-style rating for this content. Examples include Y, G, PG, PG-13 and R
 * @property createDateTimeStr The date this GIF was added to the GIPHY database
 * @property images An object containing data for various available formats and sizes of this GIF
 * @property title The title that appears on giphy.com for this GIF
 * @property altText Alt text enables assistive programs to read descriptions of GIFs
 */
data class GIF(
    @SerializedName("id")
    val id: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("bitly_url")
    val bitlyUrl: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("create_datetime")
    val createDateTimeStr: String,
    @SerializedName("images")
    val images: ImagesObject,
    @SerializedName("title")
    val title: String,
    @SerializedName("alt_text")
    val altText: String,
) {
    val createDateTime: LocalDateTime
        get() {
            return try {
                LocalDateTime.parse(createDateTimeStr)
            } catch (e: DateTimeParseException) {
                Log.e("GIF", e.message, e)
                return LocalDateTime.now()
            }
        }
}