package com.example.gamesshare.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val FIRE_STORE_NAME_COLLECTION = "Users"
    const val FIRE_STORE_COMMENTS_COLLECTION = "Comments"

    //dataStore
    val USER_EMAIL = stringPreferencesKey("Email")
    val USER_NAME = stringPreferencesKey("UserName")
    val USER_IMAGE = stringPreferencesKey("Image")
    const val IS_LOGGED_IN = "is_logged_in"
    const val USER_PREFERENCES_NAME = "UserEmail"

    //Retrofit
    const val BASE_URL = "https://api.rawg.io/api/"
    const val END_POINT_GAMES = "games"
    const val END_POINT_MOVIE = "movies"
    const val API_KEY = "?key=e2be01657a994875a4e9a725ca0420d6"
}


/**
 *  // All games
 * https://api.rawg.io/api/games?key=e2be01657a994875a4e9a725ca0420d6
 *
 * // ScreenSho de los juegos -- {game_pk} = id
 * https://api.rawg.io/api/games/{game_pk}/screenshots?key=e2be01657a994875a4e9a725ca0420d6
 *
 * // get game by ID
 * https://api.rawg.io/api/games/{id}?key=e2be01657a994875a4e9a725ca0420d6
 *
 * youtube?
 */
