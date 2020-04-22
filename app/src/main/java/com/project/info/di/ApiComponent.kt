package com.project.info.di

import com.project.info.network.InfoApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: InfoApiService)

}