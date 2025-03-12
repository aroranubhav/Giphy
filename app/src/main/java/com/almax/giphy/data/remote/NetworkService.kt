package com.almax.giphy.data.remote

import com.almax.giphy.data.model.GifResponse
import com.almax.giphy.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("trending")
    suspend fun getGifs(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("offset") offset: Int = 0
    ): GifResponse
}