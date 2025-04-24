package com.example.gamesshare.domain.network.api.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gamesshare.domain.models.GameMovieModel
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.api.repository.ApiRepository
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.domain.network.firebase.model.UserModelDomain
import com.example.gamesshare.domain.network.firebase.repository.FirebaseRepository
import javax.inject.Inject

class FetchMoviesUserCase @Inject constructor(private val apiRepository: ApiRepository) {
    suspend operator fun invoke(id: String): ResponseStatus<List<GameMovieModel>> {
        return apiRepository.getMovieOfGame(id)
    }
}