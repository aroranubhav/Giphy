package com.almax.giphy.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.almax.giphy.data.repository.FavouritesRepository
import com.almax.giphy.di.FragmentContext
import com.almax.giphy.ui.base.ViewModelProviderFactory
import com.almax.giphy.ui.favourites.FavouritesViewModel
import com.almax.giphy.ui.gif.GifAdapter
import com.almax.giphy.util.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class FavouritesModule(
    private val fragment: Fragment,
    private val context: Context
) {

    @FragmentContext
    @Provides
    fun provideContext(): Context =
        context

    @Provides
    fun provideFavouritesViewModel(
        repository: FavouritesRepository,
        dispatcherProvider: DispatcherProvider
    ): FavouritesViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(
                FavouritesViewModel::class
            ) {
                FavouritesViewModel(repository, dispatcherProvider)
            })[FavouritesViewModel::class]
    }

    @Provides
    fun provideAdapter(): GifAdapter =
        GifAdapter(
            fragment,
            arrayListOf()
        )
}