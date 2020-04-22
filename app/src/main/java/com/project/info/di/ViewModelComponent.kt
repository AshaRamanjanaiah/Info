package com.project.info.di

import com.project.info.viewmodel.InfoViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {

    fun inject(infoViewModel: InfoViewModel)
}