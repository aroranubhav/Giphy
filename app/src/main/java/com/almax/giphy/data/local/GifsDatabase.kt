package com.almax.giphy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [GifEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class GifsDatabase : RoomDatabase() {

    abstract fun gifsDao(): GifsDao
}