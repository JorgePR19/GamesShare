package com.example.gamesshare.domain.network.firebase.domain.login

import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.domain.network.firebase.repository.FirebaseRepository
import javax.inject.Inject

class LoginEmailUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): ResponseStatus<FirebaseLoginResponse> {
        return firebaseRepository.getLoginWithEmail(email, password)
    }
}