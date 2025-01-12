package com.example.gamesshare.domain.network.firebase.di

import com.example.gamesshare.domain.network.firebase.repository.FirebaseRepository
import com.example.gamesshare.domain.network.firebase.repository.FirebaseTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetFirebaseRepositoryModule {
    @Binds
    abstract fun bindFirebaseRepositoryTask(
        firebaseRepository: FirebaseRepository
    ): FirebaseTask
}