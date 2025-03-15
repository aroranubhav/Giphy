package com.almax.giphy.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.almax.giphy.data.model.GifData
import com.almax.giphy.util.Constants.GIFS_TABLE

@Entity(tableName = GIFS_TABLE)
data class GifEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    val gif: GifData,
    var isSaved: Boolean = false,
    var savedTimeStamp: String = ""
) {
    init {
        id = gif.id
    }
}
