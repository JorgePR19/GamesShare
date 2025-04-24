package com.example.gamesshare.domain.network.api.mapper

import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.models.RatingsValuesDomain
import com.example.gamesshare.domain.models.ShortScreenShotsDomain
import com.example.gamesshare.domain.network.api.data.dto.GameListDTO


class GamesDTOMapper {
    fun fromDtoToDomain(game: GameListDTO): GamesModel {
        return GamesModel(
            game.idGame,
            game.nameGame,
            game.backgroundImage,
            game.metacritic,
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
            },
            short_screenshots = game.short_screenshots.map {
                ShortScreenShotsDomain(image = it.image)
            },
            allPlatforms = game.allPlatforms.map {
                it.platform.name
            }
        )
    }

    fun fromDtoListToDomainList(gamesDTOList: List<GameListDTO>): List<GamesModel> {
        return gamesDTOList.map { fromDtoToDomain(it) }
    }
}


