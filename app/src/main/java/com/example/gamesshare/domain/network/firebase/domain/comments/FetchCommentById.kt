package com.example.gamesshare.domain.network.firebase.domain.comments

import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.domain.network.firebase.model.CommentsResponse
import com.example.gamesshare.domain.network.firebase.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCommentById @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(
        idGame: String
    ):  Flow<List<CommentsResponse>> {
        return firebaseRepository.getComments(idGame)
    }
}