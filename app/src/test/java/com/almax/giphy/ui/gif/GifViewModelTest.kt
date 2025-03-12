package com.almax.giphy.ui.gif

import app.cash.turbine.test
import com.almax.giphy.data.model.Downsized
import com.almax.giphy.data.model.GifData
import com.almax.giphy.data.model.GifImages
import com.almax.giphy.data.repository.GifRepository
import com.almax.giphy.ui.base.UiState
import com.almax.giphy.util.DispatcherProvider
import com.almax.giphy.util.TestDispatcherProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GifViewModelTest {

    @Mock
    private lateinit var repository: GifRepository

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var viewModel: GifViewModel

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Test
    fun `fetch gifs success empty response`() = runTest {
        doReturn(flowOf(emptyList<GifData>()))
            .`when`(repository)
            .getGifs()

        initViewModel(repository, dispatcherProvider)
        viewModel.uiState.test {
            assertEquals(UiState.Success(arrayListOf<GifData>()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch gifs success`() = runTest {
        val gifs = arrayListOf(
            GifData("", "", "", "", "", "", GifImages(Downsized()))
        )

        doReturn(flowOf(gifs))
            .`when`(repository)
            .getGifs()

        initViewModel(repository, dispatcherProvider)
        viewModel.uiState.test {
            assertEquals(UiState.Success(gifs), awaitItem())
            assertEquals(gifs.size, 1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch gifs error`() = runTest {
        val errorMessage = "an error occurred"
        doReturn(flow<List<GifData>> {
            throw IllegalArgumentException(errorMessage)
        })
            .`when`(repository)
            .getGifs()

        initViewModel(repository, dispatcherProvider)
        viewModel.uiState.test {
            assertEquals(UiState.Error(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun initViewModel(repository: GifRepository, dispatcherProvider: DispatcherProvider) {
        viewModel = GifViewModel(repository, dispatcherProvider)
    }
}