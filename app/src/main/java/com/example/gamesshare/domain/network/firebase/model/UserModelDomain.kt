package com.example.gamesshare.domain.network.firebase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModelDomain(
    var useId: String = "",
    var email: String = "",
    var userName: String = "",
    var userImage: String = ""
) : Parcelable