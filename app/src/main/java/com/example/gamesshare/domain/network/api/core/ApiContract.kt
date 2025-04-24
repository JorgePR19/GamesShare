package com.example.gamesshare.domain.network.api.core

import com.example.gamesshare.domain.network.api.data.response.GatApiGamesResponse
import com.example.gamesshare.domain.network.api.data.response.GetMovieGamesResponse
import com.example.gamesshare.domain.network.api.data.response.SimpleGatApiGamesResponse
import com.example.gamesshare.utils.Constants.API_KEY
import com.example.gamesshare.utils.Constants.END_POINT_GAMES
import com.example.gamesshare.utils.Constants.END_POINT_MOVIE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiContract {
    @GET(END_POINT_GAMES + API_KEY)
    suspend fun getGamesPaging(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): GatApiGamesResponse

    @GET("$END_POINT_GAMES/{id}/$END_POINT_MOVIE$API_KEY")
    suspend fun getMovieOfGame(@Path(value = "id") id: String): GetMovieGamesResponse

    @GET("$END_POINT_GAMES/{id}$API_KEY")
    suspend fun getGameById(@Path(value = "id") id: Int): Response<SimpleGatApiGamesResponse>

}