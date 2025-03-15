package com.almax.giphy.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.almax.giphy.data.local.GifsDao
import com.almax.giphy.data.local.GifsDatabase
import com.almax.giphy.data.remote.CacheInterceptor
import com.almax.giphy.data.remote.ErrorInterceptor
import com.almax.giphy.data.remote.NetworkService
import com.almax.giphy.di.ApplicationContext
import com.almax.giphy.di.BaseUrl
import com.almax.giphy.util.Constants.GIFS_DB
import com.almax.giphy.util.DefaultDispatcherProvider
import com.almax.giphy.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val application: Application
) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context =
        application

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String =
        "https://api.giphy.com/v1/gifs/"

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides //TODO: should this be singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(ErrorInterceptor())
            .cache(Cache(File(application.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .addNetworkInterceptor(CacheInterceptor())
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }

    @Provides //TODO: should this be singleton
    fun provideDefaultDispatcher(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }

    @Provides
    @Singleton
    fun provideGifsDao(): GifsDao {
        return Room
            .databaseBuilder(
                application,
                GifsDatabase::class.java,
                GIFS_DB
            )
            .build()
            .gifsDao()
    }
}