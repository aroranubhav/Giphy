package com.almax.giphy.data.local

import androidx.room.TypeConverter
import com.almax.giphy.data.model.GifData
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromGifData(gifData: GifData): String {
        return Gson().toJson(gifData)
    }

    @TypeConverter
    fun stringToGifData(gifData: String): GifData {
        return Gson().fromJson(gifData, GifData::class.java)
    }
}