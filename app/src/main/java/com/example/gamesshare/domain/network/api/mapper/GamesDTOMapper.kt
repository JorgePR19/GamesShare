package com.example.gamesshare.domain.network.api.mapper

import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.models.RatingsValuesDomain
import com.example.gamesshare.domain.network.api.data.dto.GameListDTO


class GamesDTOMapper {
    fun fromDtoToDomain(game: GameListDTO): GamesModel {
        return GamesModel(
            game.idGame,
            game.nameGame,
            game.backgroundImage,
            game.ratingGlobal,
            game.top,
            ratings = game.ratings.map { ratingDto ->
                RatingsValuesDomain(
                    title = ratingDto.title,
                    votes = ratingDto.votes,
                    percent = ratingDto.percent
                )
            },
            slug = game.slug,
            platforms = game.platforms.map {
                it.platform.name
            }.distinctBy { it.substringBefore(" ") }.filter {
               it.contains("PC")
                       || it.contains("PlayStation")
                       || it.contains("Xbox")
                       || it.contains("Nintendo")
                       || it.contains("Android")
                       || it.contains("Apple")
            }
        )
    }

    fun fromDtoListToDomainList(gamesDTOList: List<GameListDTO>): List<GamesModel> {
        return gamesDTOList.map { fromDtoToDomain(it) }
    }
}


