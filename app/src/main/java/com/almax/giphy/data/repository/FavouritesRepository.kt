package com.almax.giphy.data.repository

import com.almax.giphy.data.local.GifEntity
import com.almax.giphy.data.local.GifsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouritesRepository @Inject constructor(
    private val gifsDao: GifsDao
) {

    fun getFavouriteGifs(): Flow<List<GifEntity>> {
        return flow {
            emit(gifsDao.getAllGifs())
        }.map {
            it.sortedByDescending { gif ->
                gif.savedTimeStamp
            }
        }
    }

    suspend fun removeGifFromFavourites(gifEntity: GifEntity) {
        gifsDao.removeGif(gifEntity)
    }
}