package com.almax.giphy.data.repository

import app.cash.turbine.test
import com.almax.giphy.data.model.GifData
import com.almax.giphy.data.model.GifResponse
import com.almax.giphy.data.model.Meta
import com.almax.giphy.data.model.Pagination
import com.almax.giphy.data.remote.NetworkService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GifRepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService
    private lateinit var repository: GifRepository

    @Before
    fun setup() {
        repository = GifRepository(networkService)
    }

    @Test
    fun `fetch gifs success`() = runTest {
        val response = GifResponse(Meta(200), arrayListOf(), Pagination())
        doReturn(response)
            .`when`(networkService)
            .getGifs()

        repository.getGifs().test {
            assertEquals(response.meta.statusCode, 200)
            assertEquals(response.data, emptyList<GifData>())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch gifs error`() = runTest {
        val errorMessage = "Unauthorized"
        val errorCode = 401

        val response = GifResponse(Meta(errorCode, errorMessage), arrayListOf(), Pagination())
        doReturn(response)
            .`when`(networkService)
            .getGifs()

        repository.getGifs().test {
            assertEquals(response.meta.statusCode, errorCode)
            assertEquals(response.meta.status, errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }
}