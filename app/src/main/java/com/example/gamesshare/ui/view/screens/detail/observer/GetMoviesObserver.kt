package com.example.gamesshare.ui.view.screens.detail.observer

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.gamesshare.domain.models.GameMovieModel
import com.example.gamesshare.domain.models.SimpleGatApiGamesModel
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.StatusClass
import com.example.gamesshare.ui.view.screens.detail.views.detail.DetailGameViewModel
import com.example.gamesshare.utils.loader.DsLoaderView

sealed class StatusUI {
    data object Success : StatusUI()
    data object Error : StatusUI()
    data object Await : StatusUI()
}


@Composable
fun GetGamesObserver(
    state: ResponseStatus<SimpleGatApiGamesModel>?,
    eventUi: (StatusUI) -> Unit
) {
    if (state != null) {
        when (state.status) {
            StatusClass.Error -> {
                eventUi(StatusUI.Error)
                DsLoaderView.dismissLoader()
            }
            StatusClass.Loading ->Unit
            StatusClass.Success -> eventUi(StatusUI.Success)
        }
    }
}

@Composable
fun GetMoviesObserver(state: ResponseStatus<List<GameMovieModel>>?) {
    if (state != null) {
        when (state.status) {
            StatusClass.Error -> {
                DsLoaderView.dismissLoader()
            }
            StatusClass.Loading -> Unit
            StatusClass.Success -> {
                DsLoaderView.dismissLoader()
            }
        }
    }
}