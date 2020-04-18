package com.example.info.network

import com.example.info.model.CanadaInfo
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class InfoApiService{
    private val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(InfoApi::class.java)

    fun getCanadaInfo(): Single<CanadaInfo> {
        return retrofit.getInfo()
    }
}