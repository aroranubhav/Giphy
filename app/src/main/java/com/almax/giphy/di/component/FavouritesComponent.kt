package com.almax.giphy.di.component

import com.almax.giphy.di.FragmentScope
import com.almax.giphy.di.module.FavouritesModule
import com.almax.giphy.ui.favourites.FavouritesFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FavouritesModule::class]
)
interface FavouritesComponent {

    fun inject(fragment: FavouritesFragment)
}