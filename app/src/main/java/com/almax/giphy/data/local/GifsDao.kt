package com.almax.giphy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.almax.giphy.util.Constants.GIFS_TABLE

@Dao
interface GifsDao {

    @Query("SELECT * FROM $GIFS_TABLE ORDER BY savedTimeStamp DESC")
    suspend fun getAllGifs(): List<GifEntity>

    @Query("SELECT * FROM $GIFS_TABLE WHERE id = :id")
    suspend fun getGifById(id: String): GifEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveGif(gifData: GifEntity)

    @Delete
    suspend fun removeGif(gifData: GifEntity)
}