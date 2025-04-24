package com.example.gamesshare.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.gamesshare.domain.models.GamesModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NetworkStatusObserver @Inject constructor(@ApplicationContext private val context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected


    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isConnected.value = true
        }

        override fun onLost(network: Network) {
            _isConnected.value = false
        }
    }

    init{
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        _isConnected.value = connectivityManager.activeNetwork != null
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}