package com.example.info.di

import com.example.info.network.InfoApi
import com.example.info.network.InfoApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {

    private val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

    @Provides
    open fun provideInfoApi(): InfoApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(InfoApi::class.java)
    }

    @Provides
    open fun provideInfoApiService(): InfoApiService {
        return InfoApiService()
    }
}