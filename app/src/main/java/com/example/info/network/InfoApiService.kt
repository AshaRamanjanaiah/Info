package com.example.info.network

import com.example.info.di.DaggerApiComponent
import com.example.info.model.CanadaInfo
import io.reactivex.Single
import javax.inject.Inject

class InfoApiService{

    @Inject
    lateinit var api: InfoApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCanadaInfo(): Single<CanadaInfo> {
        return api.getInfo()
    }
}