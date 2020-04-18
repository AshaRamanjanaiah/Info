package com.example.info.network

import com.example.info.model.CanadaInfo
import io.reactivex.Single
import retrofit2.http.GET

interface InfoApi {
    @GET("facts.json")
    fun getInfo(): Single<CanadaInfo>
}