package com.example.gamesshare.domain.network.api.data.response

import com.example.gamesshare.domain.network.api.data.dto.GameListDTO
import com.example.gamesshare.domain.network.api.data.dto.GameMovieDTO
import com.google.gson.annotations.SerializedName


data class GatApiGamesResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("results")val results: List<GameListDTO>
)

data class SimpleGatApiGamesResponse(
    @SerializedName("description_raw")val description: String,
    @SerializedName("website")val website: String
)

data class GetMovieGamesResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("results")val results: List<GameMovieDTO>
)

