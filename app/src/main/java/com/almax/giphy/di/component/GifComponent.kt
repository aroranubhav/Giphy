package com.almax.giphy.di.component

import com.almax.giphy.di.ActivityScope
import com.almax.giphy.di.module.GifModule
import com.almax.giphy.ui.gif.GifActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [GifModule::class]
)
interface GifComponent {

    fun inject(activity: GifActivity)
}