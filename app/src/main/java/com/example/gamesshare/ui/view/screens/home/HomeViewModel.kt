package com.example.gamesshare.ui.view.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.network.api.data.GamesDataSource
import com.example.gamesshare.domain.network.api.repository.ApiRepository
import com.example.gamesshare.utils.NetworkStatusObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val networkStatusObserver: NetworkStatusObserver
) : ViewModel() {

    val localList = mutableStateListOf<GamesModel>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val gamePage = networkStatusObserver.isConnected.flatMapLatest {
        Pager(PagingConfig(pageSize = 18)) {
            GamesDataSource(apiRepository, networkStatusObserver)
        }.flow
    }.cachedIn(viewModelScope)
}