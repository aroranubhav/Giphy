package com.almax.giphy.di.component

import com.almax.giphy.GifApplication
import com.almax.giphy.data.remote.NetworkService
import com.almax.giphy.di.module.ApplicationModule
import com.almax.giphy.util.DispatcherProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class]
)
interface ApplicationComponent {

    fun inject(application: GifApplication)

    fun getNetworkService(): NetworkService

    fun getDispatcherProvider(): DispatcherProvider
}