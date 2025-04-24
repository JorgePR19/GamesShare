package com.example.gamesshare.domain.network.firebase.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.gamesshare.R
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.StatusClass
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.domain.network.firebase.di.FirebaseInstance
import com.example.gamesshare.domain.network.firebase.model.CommentsResponse
import com.example.gamesshare.domain.network.firebase.model.UserModelDomain
import com.example.gamesshare.domain.network.firebase.model.UserModelResponse
import com.example.gamesshare.domain.network.makeNetWorkCall
import com.example.gamesshare.utils.Constants.FIRE_STORE_COMMENTS_COLLECTION
import com.google.firebase.firestore.FieldValue.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
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

    suspend fun getRegisterComments(
        idGame: String,
        userName: String,
        userImage: String,
        comment: String,
        date: String
    ): ResponseStatus<FirebaseLoginResponse>

    suspend fun getComments(
        idGame: String
    ): Flow<List<CommentsResponse>>

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

    //---------------------------------------------------- Comments
    override suspend fun getRegisterComments(
        idGame: String,
        userName: String,
        userImage: String,
        comment: String,
        date: String
    ): ResponseStatus<FirebaseLoginResponse> {
        return withContext(Dispatchers.IO) {
            val registerDeferred =
                async { registerComment(idGame, userName, userImage, comment, date) }
            val registerResponse = registerDeferred.await()
            registerResponse
        }
    }

    private suspend fun registerComment(
        idGame: String,
        userName: String,
        userImage: String,
        comment: String,
        date: String
    ): ResponseStatus<FirebaseLoginResponse> = makeNetWorkCall {
        val response = createComment(idGame, userName, userImage, comment, date)
        response
    }


    private suspend fun createComment(
        idGame: String,
        userName: String,
        userImage: String,
        comment: String,
        date: String
    ): FirebaseLoginResponse =
        suspendCoroutine { continuation ->
            val documentRef = firebaseInstance.getFireStoreComments().document(idGame)
            val nuevoComentario = CommentsResponse(
                idGame = idGame,
                userName = userName,
                userImage = userImage,
                comment = comment,
                date = date
            )

            // Intentar agregar el comentario al array existente
            documentRef.update(
                FIRE_STORE_COMMENTS_COLLECTION,
                com.google.firebase.firestore.FieldValue.arrayUnion(nuevoComentario)
            )
                .addOnCompleteListener { task ->
                    val result = if (task.isSuccessful) {
                        FirebaseLoginResponse.FirebaseSuccess
                    } else {
                        if (task.exception?.message?.contains("No document to update") == true) {
                            // Si el documento no existe, lo creamos con el primer comentario
                            documentRef.set(
                                mapOf(
                                    FIRE_STORE_COMMENTS_COLLECTION to listOf(
                                        nuevoComentario
                                    )
                                )
                            )
                                .addOnCompleteListener { innerTask ->
                                    val innerResult = if (innerTask.isSuccessful) {
                                        FirebaseLoginResponse.FirebaseSuccess
                                    } else {
                                        FirebaseLoginResponse.FirebaseError(
                                            innerTask.exception?.message
                                                ?: context.getString(R.string.unknow_error)
                                        )
                                    }
                                    continuation.resume(innerResult)
                                }
                            return@addOnCompleteListener
                        }
                        FirebaseLoginResponse.FirebaseError(
                            task.exception?.message ?: context.getString(R.string.unknow_error)
                        )
                    }
                    continuation.resume(result)
                }
        }


    //---------------------------------------------------- Comments fetch

    override suspend fun getComments(idGame: String): Flow<List<CommentsResponse>> {
        return withContext(Dispatchers.IO) {
            val registerDeferred = async { observeCommentsById(idGame) }
            val registerResponse = registerDeferred.await()
            registerResponse
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun observeCommentsById(idGame: String): Flow<List<CommentsResponse>> = callbackFlow {
        val listenerRegistration = firebaseInstance.getFireStoreComments()
            .document(idGame)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Cierra el flujo con un error si ocurre un problema
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val commentsList = snapshot.get("Comments") as? List<Map<String, Any>>
                    val result = commentsList?.map { comment ->
                        CommentsResponse(
                            comment = comment["comment"] as? String ?: "",
                            userName = comment["userName"] as? String ?: "",
                            userImage = comment["userImage"] as? String ?: "",
                            idGame = comment["idGame"] as? String ?: "",
                            date = comment["date"] as? String ?: ""
                        )
                    } ?: emptyList()

                    val formatter =
                        DateTimeFormatter.ofPattern("dd/MM/yyyy - h:mm a", Locale.ENGLISH)
                    val sortList =
                        result.sortedByDescending { LocalDateTime.parse(it.date, formatter) }

                    trySend(sortList).isSuccess
                } else {
                    // Envía una lista vacía si no hay datos
                    trySend(emptyList()).isSuccess
                }
            }

        // Cierra el flujo cuando se cancela
        awaitClose { listenerRegistration.remove() }
    }

}
