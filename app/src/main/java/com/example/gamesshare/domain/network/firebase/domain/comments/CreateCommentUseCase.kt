package com.example.gamesshare.domain.network.firebase.domain.comments

import com.example.gamesshare.domain.models.GameMovieModel
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.domain.network.firebase.repository.FirebaseRepository
import com.google.type.Date
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(
        idGame: String,
        userName: String,
        userImage: String,
        comment: String,
        date: String
    ): ResponseStatus<FirebaseLoginResponse> {
        return firebaseRepository.getRegisterComments(idGame, userName, userImage, comment, date
        )
    }
}