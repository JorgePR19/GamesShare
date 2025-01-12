package com.example.gamesshare.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val FIRE_STORE_NAME_COLLECTION = "Users"

    //dataStore
    val USER_EMAIL = stringPreferencesKey("Email")
    val USER_NAME = stringPreferencesKey("UserName")
    val USER_IMAGE = stringPreferencesKey("Image")
    const val IS_LOGGED_IN = "is_logged_in"
    const val USER_PREFERENCES_NAME = "UserEmail"
}