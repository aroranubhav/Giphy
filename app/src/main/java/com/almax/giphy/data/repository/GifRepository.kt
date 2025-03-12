package com.almax.giphy.data.repository

import com.almax.giphy.data.model.GifData
import com.almax.giphy.data.remote.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GifRepository @Inject constructor(
    private val networkService: NetworkService
) {

    fun getGifs(): Flow<List<GifData>> {
        return flow {
            emit(networkService.getGifs())
        }.map {
            it.data
            /* discuss
            if (it.meta.statusCode == 200) {
                it.data
            } else {
                arrayListOf()
            }*/
        }
    }
}