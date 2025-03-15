package com.almax.giphy.data.repository

import com.almax.giphy.data.local.GifEntity
import com.almax.giphy.data.local.GifsDao
import com.almax.giphy.data.model.GifData
import com.almax.giphy.data.model.toGifEntity
import com.almax.giphy.data.remote.NetworkService
import com.almax.giphy.util.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GifRepository @Inject constructor(
    private val networkService: NetworkService,
    private val gifsDao: GifsDao,
    dispatcherProvider: DispatcherProvider
) {

    private val scope = CoroutineScope(dispatcherProvider.default)

    fun getGifs(): Flow<List<GifEntity>> {
        return flow {
            val gifsFromRemote = networkService.getGifs()
            val gifEntityList = updateGifDataToEntity(gifsFromRemote.data)
            emit(gifEntityList)
        }
        /*return flow {
            emit(networkService.getGifs())
        }.map {
            it.data
            *//* discuss
            if (it.meta.statusCode == 200) {
                it.data
            } else {
                arrayListOf()
            }*//*
        }*/
    }

    private suspend fun updateGifDataToEntity(remoteGifs: List<GifData>): List<GifEntity> {
        val gifs = mutableListOf<GifEntity>()
        val savedGifs: List<GifEntity> = gifsDao.getAllGifs()
        val job = scope.async {
            if (savedGifs.isNotEmpty()) {
                for (remoteGif in remoteGifs) {
                    var isAdded = false
                    for (savedGif in savedGifs) {
                        if (remoteGif.id == savedGif.id) {
                            val updatedGif = remoteGif.toGifEntity(
                                true,
                                savedGif.savedTimeStamp
                            )
                            gifs.add(updatedGif)
                            isAdded = true
                            break
                        }
                    }
                    if (!isAdded) {
                        gifs.add(
                            remoteGif.toGifEntity(
                                false,
                                ""
                            )
                        )
                    }
                }
            } else {
                for (remoteGif in remoteGifs) {
                    gifs.add(remoteGif.toGifEntity(false, ""))
                }
            }
        }
        job.await()
        return gifs
    }

    suspend fun updateGifEntityInDb(gifEntity: GifEntity, isSaved: Boolean) {
        if (isSaved) {
            gifsDao.saveGif(gifEntity)
        } else {
            gifsDao.removeGif(gifEntity)
        }
    }
}