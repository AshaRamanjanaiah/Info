package com.project.info

import com.project.info.di.ApiModule
import com.project.info.network.InfoApiService

class ApiModuleTest(val mockApiService: InfoApiService): ApiModule() {
    override fun provideInfoApiService(): InfoApiService {
        return mockApiService
    }
}