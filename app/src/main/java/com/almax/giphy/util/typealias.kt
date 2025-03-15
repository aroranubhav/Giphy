package com.almax.giphy.util

typealias GifItemSavedListener<T> = (data: T, isSaved: Boolean) -> Unit

typealias GifShareListener<T> = (data: T) -> Unit