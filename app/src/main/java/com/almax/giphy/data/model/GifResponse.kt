package com.almax.giphy.data.model

data class GifResponse(
    val meta: Meta,
    val data: List<GifData> = arrayListOf(),
    val pagination: Pagination
)
