package com.example.gamesshare.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class GamesModel(
    val gameId: Int,
    val gameName: String,
    val backgroundImage: String,
    val ratingGlobal: Double,
    val top: Int,
    val ratings: List<RatingsValuesDomain>,
    val slug: String,
    val platforms: List<String>
):Parcelable

@Parcelize
@Serializable
data class RatingsValuesDomain(
    val title: String,
    val votes: Int,
    val percent: Double,
):Parcelable