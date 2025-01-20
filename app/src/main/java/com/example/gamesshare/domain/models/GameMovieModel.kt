package com.example.gamesshare.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class GameMovieModel(
    val nameGame: String,
    val preview: String,
    val data: DataMovie,
):Parcelable

@Parcelize
@Serializable
data class DataMovie(
    val lowQuality: String,
    val hideQuality: String,
):Parcelable

data class SimpleGatApiGamesModel(
    val description: String,
    val website: String
)