package com.example.info.di

import com.example.info.network.InfoApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: InfoApiService)

}