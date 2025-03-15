package com.almax.giphy.data.model

import com.almax.giphy.data.local.GifEntity
import com.google.gson.annotations.SerializedName

data class GifData(
    val type: String = "",
    val id: String = "",
    val url: String = "",
    val slug: String = "",
    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String = "",
    val title: String = "",
    val images: GifImages
)

fun GifData.toGifEntity(isSaved: Boolean, timeStamp: String): GifEntity {
    return GifEntity("", this, isSaved, timeStamp)
}
