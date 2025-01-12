package com.example.gamesshare.domain.network

sealed class StatusClass {
    data object Success : StatusClass()
    data object Loading : StatusClass()
    data object Error : StatusClass()
}

data class ResponseStatus<out T>(
    val status: StatusClass,
    val data: T? = null,
    val message: Int? = null
){
   companion object{
       fun <T> success(data: T):ResponseStatus<T>{
           return ResponseStatus(StatusClass.Success, data)
       }

       fun <T> error(message: Int):ResponseStatus<T>{
           return ResponseStatus(StatusClass.Error, message = message)
       }
       fun <T> loading():ResponseStatus<T>{
           return ResponseStatus(StatusClass.Loading)
       }
   }
}