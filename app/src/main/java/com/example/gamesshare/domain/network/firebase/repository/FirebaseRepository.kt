package com.example.gamesshare.domain.network.firebase.repository

import android.content.Context
import com.example.gamesshare.R
import com.example.gamesshare.domain.local.DataStorePref
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.StatusClass
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.domain.network.firebase.di.FirebaseInstance
import com.example.gamesshare.domain.network.firebase.model.UserModelDomain
import com.example.gamesshare.domain.network.firebase.model.UserModelResponse
import com.example.gamesshare.domain.network.makeNetWorkCall
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


interface FirebaseTask {
    suspend fun getLoginWithEmail(
        email: String,
        password: String
    ): ResponseStatus<FirebaseLoginResponse>

    suspend fun getRegisterWithEmail(
        email: String,
        password: String,
        userName: String,
        userImage: String
    ): ResponseStatus<FirebaseLoginResponse>

    /* fun isSignInActive(): Boolean

     fun signOut()
*/
    fun fetchUserName(user: (UserModelDomain) -> Unit)
}

class FirebaseRepository @Inject constructor(
    private val firebaseInstance: FirebaseInstance,
    @ApplicationContext private val context: Context,
) : FirebaseTask {
    override suspend fun getLoginWithEmail(
        email: String,
        password: String
    ): ResponseStatus<FirebaseLoginResponse> {
        return withContext(Dispatchers.IO) {
            val loginDeferred = async { loginFirebase(email, password) }
            val loginResponse = loginDeferred.await()
            loginResponse
        }
    }

    private suspend fun loginFirebase(
        email: String,
        password: String
    ): ResponseStatus<FirebaseLoginResponse> = makeNetWorkCall {
        val response = doLogin(email, password)
        response
    }

    private suspend fun doLogin(email: String, password: String): FirebaseLoginResponse =
        suspendCoroutine { continuation ->
            firebaseInstance.getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    val config =
                        if (task.isSuccessful) FirebaseLoginResponse.FirebaseSuccess else FirebaseLoginResponse.FirebaseError(
                            task.exception?.message ?: context.getString(R.string.unknow_error)
                        )
                    continuation.resume(config)
                }
        }

    //-------------------------------------------------
    override suspend fun getRegisterWithEmail(
        email: String,
        password: String,
        userName: String,
        userImage: String
    ): ResponseStatus<FirebaseLoginResponse> {
        return withContext(Dispatchers.IO) {
            val registerDeferred = async { registerFirebase(email, password) }
            val registerResponse = registerDeferred.await()

            if (registerResponse.status is StatusClass.Success) {
                val saveUserDeferred = async { saveUserFirebase(userName, userImage) }
                val saveResponse = saveUserDeferred.await()
                saveResponse
            } else {
                registerResponse
            }

        }
    }

    private suspend fun registerFirebase(
        email: String,
        password: String
    ): ResponseStatus<FirebaseLoginResponse> = makeNetWorkCall {
        val response = createRegister(email, password)
        response
    }

    private suspend fun saveUserFirebase(
        userName: String,
        userImage: String
    ): ResponseStatus<FirebaseLoginResponse> = makeNetWorkCall {
        val response = saveUser(userName, userImage)
        response
    }

    private suspend fun createRegister(
        email: String,
        password: String,
    ): FirebaseLoginResponse =
        suspendCoroutine { continuation ->
            firebaseInstance.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    val result =
                        if (task.isSuccessful) FirebaseLoginResponse.FirebaseSuccess else FirebaseLoginResponse.FirebaseError(
                            task.exception?.message ?: context.getString(R.string.unknow_error)
                        )
                    continuation.resume(result)
                }
        }

    private suspend fun saveUser(
        userName: String,
        userImage: String
    ): FirebaseLoginResponse =
        suspendCoroutine { continuation ->
            val id = firebaseInstance.getAuth().currentUser?.uid
            val email = firebaseInstance.getAuth().currentUser?.email

            val user = UserModelResponse(
                useId = id.toString(),
                email = email.toString(),
                userName = userName,
                userImage = userImage
            )
            firebaseInstance.getFireStore().add(user).addOnCompleteListener { task ->
                val result =
                    if (task.isSuccessful) {
                        FirebaseLoginResponse.FirebaseSuccess
                    } else FirebaseLoginResponse.FirebaseError(
                        task.exception?.message ?: context.getString(R.string.unknow_error)
                    )
                continuation.resume(result)
            }
        }

    //-------------------------------------

    /*override fun isSignInActive(): Boolean =
        firebaseInstance.getAuntInstance().currentUser?.email.isNullOrEmpty()

    override fun signOut() {
        firebaseInstance.getAuth().signOut()
    }*/

    override fun fetchUserName(user: (UserModelDomain) -> Unit) {
        val email = firebaseInstance.getAuth().currentUser?.email
        val documents = UserModelDomain()

        firebaseInstance.getFireStore().whereEqualTo("email", email.toString())
            .addSnapshotListener { query, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (query != null) {
                    for (document in query) {
                        val myDocument = document.toObject(UserModelDomain::class.java).copy(
                            useId = document.id
                        )
                        val newDocument = documents.copy(
                            userName = myDocument.userName,
                            useId = myDocument.useId,
                            email = myDocument.email,
                            userImage = myDocument.userImage
                        )
                        user.invoke(newDocument)
                    }
                }
            }
    }
}