package com.example.gamesshare

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamesshare.domain.local.DataStorePref
import com.example.gamesshare.domain.local.SharePreference
import com.example.gamesshare.domain.models.DataMovie
import com.example.gamesshare.domain.models.GameMovieModel
import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.firebase.domain.login.FetchUserUserCase
import com.example.gamesshare.ui.view.navigation.HomeDestination
import com.example.gamesshare.ui.view.navigation.LoginFirsTimeDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharePreferenceViewModel @Inject constructor(
    private val sharePreference: SharePreference,
    private val fetchUserUserCase: FetchUserUserCase,
    private val dataStorePref: DataStorePref
) :
    ViewModel() {

    val email = dataStorePref.getEmail
    val userName = dataStorePref.getUserName
    val userImage = dataStorePref.getUserImage

    var listMovies: List<GameMovieModel> = emptyList()

    private val _state = MutableLiveData<ResponseStatus<Any>>()
    val state: LiveData<ResponseStatus<Any>> = _state

    fun getStartDestination() {
        val isLoggedIn = sharePreference.isLoggedIn()
        _state.value =
            ResponseStatus.success(if (!isLoggedIn) LoginFirsTimeDestination else HomeDestination)
    }

    fun setStarDestination(value: Boolean) {
        //  falso muestra el login
        // verdadero muestra el home
        sharePreference.saveLoginState(value)
    }

    fun savaDataUser() {
        viewModelScope.launch {
            fetchUserUserCase.getUser {
                CoroutineScope(Dispatchers.IO).launch {
                    dataStorePref.saveDataUser(it.email, it.userName, it.userImage)
                }
            }
        }
    }
}