package com.example.gamesshare.domain.network.api.data.dto

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json


data class GameListDTO(
    @SerializedName("id") val idGame: Int,
    @SerializedName("name") val nameGame: String,
    @SerializedName("background_image") val backgroundImage: String,
    @SerializedName("rating") val ratingGlobal: Double,
    @SerializedName("rating_top") val top: Int,
    @SerializedName("ratings") val ratings: List<RatingsValuesDTO>,
    @SerializedName("slug") val slug: String,
    @SerializedName("platforms") val platforms: List<PlatformWrapperDTO>
)

data class RatingsValuesDTO(
    @SerializedName("title") val title: String,
    @SerializedName("count") val votes: Int,
    @SerializedName("percent") val percent: Double,
)

data class PlatformWrapperDTO(
    @SerializedName("platform") val platform: PlatformDTO
)
data class PlatformDTO(
    @SerializedName("name") val name: String,
)