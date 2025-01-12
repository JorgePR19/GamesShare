package com.example.gamesshare.domain.network.firebase.di

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.gamesshare.utils.Constants.USER_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Delegado para crear DataStore
val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): androidx.datastore.core.DataStore<Preferences> {
        return context.dataStore
    }
}