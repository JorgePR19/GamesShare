package com.example.gamesshare.domain.network.api.data.dto

import com.google.gson.annotations.SerializedName

data class GameMovieDTO(
    @SerializedName("name") val nameGame: String,
    @SerializedName("preview") val preview: String,
    @SerializedName("data") val data: DataMovieDTO,
)

data class DataMovieDTO(
    @SerializedName("480") val lowQuality: String,
    @SerializedName("max") val hideQuality: String,
)