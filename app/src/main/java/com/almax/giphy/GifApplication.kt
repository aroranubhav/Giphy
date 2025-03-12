package com.almax.giphy

import android.app.Application
import com.almax.giphy.di.component.ApplicationComponent
import com.almax.giphy.di.component.DaggerApplicationComponent
import com.almax.giphy.di.module.ApplicationModule

class GifApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        component = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this@GifApplication))
            .build()

        component.inject(this@GifApplication)
    }
}