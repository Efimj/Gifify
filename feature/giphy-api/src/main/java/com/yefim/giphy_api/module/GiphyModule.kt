package com.yefim.giphy_api.module

import com.yefim.giphy_api.api.GiphySearchApi
import com.yefim.giphy_api.api.GiphySearchByIDApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GiphyModule {
    private const val GIPHY_URL = "https://api.giphy.com/v1/gifs/"

    @Provides
    @Singleton
    fun provideGiphySearchApi(): GiphySearchApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(GIPHY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GiphySearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGiphySearchByIDApi(): GiphySearchByIDApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(GIPHY_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GiphySearchByIDApi::class.java)
    }
}