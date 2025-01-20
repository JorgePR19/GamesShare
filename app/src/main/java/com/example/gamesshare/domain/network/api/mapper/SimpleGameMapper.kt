package com.example.gamesshare.domain.network.api.mapper

import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.models.RatingsValuesDomain
import com.example.gamesshare.domain.models.SimpleGatApiGamesModel
import com.example.gamesshare.domain.network.api.data.dto.GameListDTO
import com.example.gamesshare.domain.network.api.data.response.SimpleGatApiGamesResponse

class SimpleGameMapper {
    fun fromDtoToDomain(game: SimpleGatApiGamesResponse): SimpleGatApiGamesModel {
        return SimpleGatApiGamesModel(
            description = game.description,
            website = game.website.ifEmpty { "Sin pagina web" }
        )
    }
}