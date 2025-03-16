package com.almax.giphy.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almax.giphy.data.local.GifEntity
import com.almax.giphy.data.repository.FavouritesRepository
import com.almax.giphy.ui.base.UiState
import com.almax.giphy.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val repository: FavouritesRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<GifEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<GifEntity>>>
        get() = _uiState

    init {
        getFavouriteGifs()
    }

    private fun getFavouriteGifs() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getFavouriteGifs()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun removeGifFromFavourites(gifEntity: GifEntity) {
        viewModelScope.launch(dispatcherProvider.io) {
            repository.removeGifFromFavourites(gifEntity)
        }
    }
}