package com.example.info.di

import com.example.info.viewmodel.InfoViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {

    fun inject(infoViewModel: InfoViewModel)
}