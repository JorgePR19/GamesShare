package com.example.gamesshare.domain.local

import android.content.Context
import com.example.gamesshare.utils.Constants.IS_LOGGED_IN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharePreference @Inject constructor(@ApplicationContext private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)


    // Guardar el estado de inicio de sesión
    fun saveLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply()
    }

    // Leer el estado de inicio de sesión
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

}