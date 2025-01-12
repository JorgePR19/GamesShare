package com.example.gamesshare.domain.network.firebase.di

import com.example.gamesshare.utils.Constants.FIRE_STORE_NAME_COLLECTION
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseInstance @Inject constructor() {
    private val auntInstance = FirebaseAuth.getInstance()
    private val auth: FirebaseAuth = Firebase.auth
    private val fireStore = FirebaseFirestore.getInstance().collection(FIRE_STORE_NAME_COLLECTION)

    fun getAuth() = auth
    fun getFireStore() = fireStore
    fun getAuntInstance() = auntInstance
}