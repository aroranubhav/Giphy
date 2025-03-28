package com.almax.giphy.ui.gif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almax.giphy.data.local.GifEntity
import com.almax.giphy.data.repository.GifRepository
import com.almax.giphy.ui.base.UiState
import com.almax.giphy.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class GifViewModel(
    private val repository: GifRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GifEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<GifEntity>>>
        get() = _uiState

    init {
        getGifs()
    }

    private fun getGifs() {
        viewModelScope.launch(dispatcher.main) {
            repository.getGifs()
                .flowOn(dispatcher.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect { gifs ->
                    _uiState.value = UiState.Success(gifs)
                }
        }
    }

    fun updateGifEntityInDb(gifEntity: GifEntity, isSaved: Boolean) {
        viewModelScope.launch(dispatcher.io) {
            repository.updateGifEntityInDb(gifEntity, isSaved)
        }
    }
}