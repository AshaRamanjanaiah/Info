package com.project.info.network

import com.project.info.di.DaggerApiComponent
import com.project.info.model.CanadaInfo
import io.reactivex.Single
import javax.inject.Inject

class InfoApiService {

    @Inject
    lateinit var api: InfoApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCanadaInfo(): Single<CanadaInfo> {
        return api.getInfo()
    }
}