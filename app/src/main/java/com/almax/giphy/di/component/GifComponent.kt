package com.almax.giphy.di.component

import com.almax.giphy.di.FragmentScope
import com.almax.giphy.di.module.GifModule
import com.almax.giphy.ui.gif.GifFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [GifModule::class]
)
interface GifComponent {

    fun inject(fragment: GifFragment)
}