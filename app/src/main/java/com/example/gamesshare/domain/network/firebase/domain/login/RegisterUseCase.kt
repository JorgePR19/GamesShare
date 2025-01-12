package com.example.gamesshare.domain.network.firebase.domain.login

import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.domain.network.firebase.repository.FirebaseRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
        username: String,
        userImage:String
    ): ResponseStatus<FirebaseLoginResponse> {
        return firebaseRepository.getRegisterWithEmail(email = email, password = password, userName = username, userImage = userImage)
    }
}