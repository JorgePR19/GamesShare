package com.example.gamesshare.domain.network.api.domain

import com.example.gamesshare.domain.models.SimpleGatApiGamesModel
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.api.repository.ApiRepository
import javax.inject.Inject

class GetGameByIdUserCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(id: Int): ResponseStatus<SimpleGatApiGamesModel> {
        return apiRepository.getGameById(id)
    }
}