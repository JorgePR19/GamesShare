package com.example.gamesshare.domain.network.api.mapper

import com.example.gamesshare.domain.models.DataMovie
import com.example.gamesshare.domain.models.GameMovieModel
import com.example.gamesshare.domain.network.api.data.dto.GameMovieDTO

class MoviesDTOMapper {
    fun fromDtoToDomain(movie: GameMovieDTO): GameMovieModel {
        return GameMovieModel(
            nameGame = movie.nameGame,
            preview = movie.preview,
            data = DataMovie(
                lowQuality = movie.data.lowQuality,
                hideQuality = movie.data.hideQuality
            )
        )
    }

    fun fromDtoListToDomainList(movie: List<GameMovieDTO>): List<GameMovieModel> {
        return movie.map { fromDtoToDomain(it) }
    }
}
