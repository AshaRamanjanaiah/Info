package com.example.info

import com.example.info.di.ApiModule
import com.example.info.network.InfoApiService

class ApiModuleTest(val mockApiService: InfoApiService): ApiModule() {
    override fun provideInfoApiService(): InfoApiService {
        return mockApiService
    }
}