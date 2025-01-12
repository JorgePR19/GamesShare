package com.example.gamesshare.domain.local

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.gamesshare.utils.Constants.USER_EMAIL
import com.example.gamesshare.utils.Constants.USER_IMAGE
import com.example.gamesshare.utils.Constants.USER_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStorePref @Inject constructor(
    private val dataStore: androidx.datastore.core.DataStore<Preferences>
) {

    val getEmail: Flow<String> = dataStore.data
        .map { pref ->
            pref[USER_EMAIL] ?: ""
        }

    val getUserName: Flow<String> = dataStore.data
        .map { pref ->
            pref[USER_NAME] ?: ""
        }

    val getUserImage: Flow<String> = dataStore.data
        .map { pref ->
            pref[USER_IMAGE] ?: ""
        }

    suspend fun saveDataUser(email: String, userName: String, image: String) {
        dataStore.edit {
            it[USER_EMAIL] = email
            it[USER_NAME] = userName
            it[USER_IMAGE] = image
        }
    }
}