package com.example.gamesshare.domain.network.firebase.core

/*sealed class FirebaseLoginResponse{
    data object FirebaseSuccess : FirebaseLoginResponse()
    data object FirebaseError : FirebaseLoginResponse()
}*/


sealed class FirebaseLoginResponse {
    data object FirebaseSuccess : FirebaseLoginResponse()
    data class FirebaseError(val messageId: String) : FirebaseLoginResponse()
}