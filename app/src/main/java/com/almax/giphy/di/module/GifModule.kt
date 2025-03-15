package com.almax.giphy.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.almax.giphy.data.repository.GifRepository
import com.almax.giphy.di.FragmentContext
import com.almax.giphy.ui.base.ViewModelProviderFactory
import com.almax.giphy.ui.gif.GifAdapter
import com.almax.giphy.ui.gif.GifViewModel
import com.almax.giphy.util.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class GifModule(
    private val fragment: Fragment,
    private val context: Context
) {

    @FragmentContext
    @Provides
    fun provideContext(): Context =
        context

    @Provides
    fun provideGifViewModel(
        repository: GifRepository,
        dispatcher: DispatcherProvider
    ): GifViewModel {
        return ViewModelProvider(
            fragment,
            ViewModelProviderFactory(GifViewModel::class) {
                GifViewModel(repository, dispatcher)
            })[GifViewModel::class]
    }

    @Provides
    fun provideGifAdapter(): GifAdapter {
        return GifAdapter(arrayListOf())
    }
}