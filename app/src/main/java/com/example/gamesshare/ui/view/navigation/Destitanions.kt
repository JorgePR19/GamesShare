package com.example.gamesshare.ui.view.navigation

import com.example.gamesshare.domain.models.GamesModel
import kotlinx.serialization.Serializable

@Serializable
object LoginFirsTimeDestination

@Serializable
object HomeDestination

@Serializable
data class DetailDestination(val detailModel: GamesModel)

@Serializable
object ListMoviesDestination