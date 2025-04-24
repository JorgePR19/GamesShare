package com.example.gamesshare.domain.network.api.di

import com.example.gamesshare.domain.network.api.repository.ApiRepository
import com.example.gamesshare.domain.network.api.repository.ApiTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class ApiRepositoryModule {
    @Binds
    abstract fun bindGetDataRepositoryTask(
        apiRepository: ApiRepository
    ): ApiTask
}