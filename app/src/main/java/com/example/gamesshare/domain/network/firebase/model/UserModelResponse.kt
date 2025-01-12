package com.example.gamesshare.domain.network.firebase.model

data class UserModelResponse(
    val email: String,
    val useId: String,
    val userName: String,
    val userImage:String
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "email" to this.email,
            "userId" to this.useId,
            "userName" to this.userName,
            "userImage" to this.userImage,
        )
    }
}