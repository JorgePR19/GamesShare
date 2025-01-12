package com.example.gamesshare.domain.network

import com.example.gamesshare.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

suspend fun <T> makeNetWorkCall(
    call: suspend () -> T
): ResponseStatus<T> = withContext(Dispatchers.IO) {
    try {
        ResponseStatus.success(call())
    } catch (e: UnknownHostException) {
        ResponseStatus.error(R.string.unknow_host_exepcion)
    } catch (e: HttpException) {
        val errorMessage = R.string.unknow_error
        ResponseStatus.error(errorMessage)
    } catch (e: Exception) {
        val errorMessage = R.string.unknow_error
        ResponseStatus.error(errorMessage)
    }
}