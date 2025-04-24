package com.example.gamesshare.domain.network.firebase.di

import com.example.gamesshare.utils.Constants.FIRE_STORE_COMMENTS_COLLECTION
import com.example.gamesshare.utils.Constants.FIRE_STORE_NAME_COLLECTION
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import javax.inject.Inject

class FirebaseInstance @Inject constructor() {
    private val auntInstance = FirebaseAuth.getInstance()
    private val auth: FirebaseAuth = Firebase.auth
    private val fireStore = FirebaseFirestore.getInstance().collection(FIRE_STORE_NAME_COLLECTION)
    private val fireStoreComments = FirebaseFirestore.getInstance().collection(FIRE_STORE_COMMENTS_COLLECTION)

    fun getAuth() = auth
    fun getFireStore() = fireStore
    fun getFireStoreComments() = fireStoreComments
    fun getAuntInstance() = auntInstance
}