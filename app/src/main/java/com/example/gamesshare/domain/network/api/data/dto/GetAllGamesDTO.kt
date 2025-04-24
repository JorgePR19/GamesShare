package com.example.gamesshare.domain.network.api.data.dto

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json


data class GameListDTO(
    @SerializedName("id") val idGame: Int,
    @SerializedName("name") val nameGame: String,
    @SerializedName("background_image") val backgroundImage: String,
    @SerializedName("metacritic") val metacritic: Int,
    @SerializedName("rating_top") val top: Int,
    @SerializedName("ratings") val ratings: List<RatingsValuesDTO>,
    @SerializedName("slug") val slug: String,
    @SerializedName("parent_platforms") val platforms: List<PlatformWrapperDTO>,
    @SerializedName("platforms") val allPlatforms: List<PlatformWrapperDTO>,
    @SerializedName("short_screenshots") val short_screenshots: List<ShortScreenShots>
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

data class ShortScreenShots(
    @SerializedName("image") val image: String
)