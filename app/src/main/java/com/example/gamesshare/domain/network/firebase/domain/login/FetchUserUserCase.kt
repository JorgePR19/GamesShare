package com.example.gamesshare.domain.network.firebase.domain.login

import com.example.gamesshare.domain.network.firebase.model.UserModelDomain
import com.example.gamesshare.domain.network.firebase.repository.FirebaseRepository
import javax.inject.Inject


class FetchUserUserCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    fun getUser(user: (UserModelDomain) -> Unit) {
        return firebaseRepository.fetchUserName {
            user.invoke(it)
        }
    }
}