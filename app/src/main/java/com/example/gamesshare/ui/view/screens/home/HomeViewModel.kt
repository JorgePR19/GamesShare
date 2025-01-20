package com.example.gamesshare.ui.view.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.gamesshare.domain.network.api.data.GamesDataSource
import com.example.gamesshare.domain.network.api.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    val gamePage = Pager(PagingConfig(pageSize = 18)){
        GamesDataSource(apiRepository)
    }.flow.cachedIn(viewModelScope) // cachedIn hace que se guarde en cache
}