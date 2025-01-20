package com.example.gamesshare.ui.view.screens.detail.views.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamesshare.domain.models.GameMovieModel
import com.example.gamesshare.domain.models.SimpleGatApiGamesModel
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.api.domain.FetchMoviesUserCase
import com.example.gamesshare.domain.network.api.domain.GetGameByIdUserCase
import com.example.gamesshare.ui.view.screens.detail.observer.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailGameViewModel @Inject constructor(
    private val fetchMoviesUserCase: FetchMoviesUserCase,
    val getGameByIdUserCase: GetGameByIdUserCase
) : ViewModel() {
    private val _observerState =
        MutableStateFlow<ResponseStatus<List<GameMovieModel>>>(ResponseStatus.loading())
    val observerState = _observerState.asStateFlow()

    private val _stateUI = mutableStateOf<StatusUI>(StatusUI.Await)
    val stateUI: State<StatusUI> get() = _stateUI

    private val _observerData =
        MutableStateFlow<ResponseStatus<SimpleGatApiGamesModel>>(ResponseStatus.loading())
    val observerData = _observerData.asStateFlow()


    fun setUIState(statusUI: StatusUI){
        _stateUI.value = statusUI
    }

    fun resetFetchData(){
        _observerData.value = ResponseStatus.loading()
        _observerState.value = ResponseStatus.loading()
    }

    fun fetchMovies(id: String) {
        viewModelScope.launch {
            _observerState.value = fetchMoviesUserCase(id)
        }
    }

    fun fetchData(id: Int) {
        viewModelScope.launch {
            _observerData.value = getGameByIdUserCase(id)
        }
    }
}