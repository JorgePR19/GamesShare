package com.example.gamesshare.ui.view.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gamesshare.SharePreferenceViewModel
import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.ui.theme.textLoader
import com.example.gamesshare.ui.view.components.CardLessSkeleton
import com.example.gamesshare.ui.view.components.GridItemHomeScreen
import com.example.gamesshare.ui.view.components.HeaderHomeInfo
import com.example.gamesshare.ui.view.components.WavesBackground


@Composable
fun HomeScreen(
    sharePreferenceViewModel: SharePreferenceViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToMovies: (GamesModel) -> Unit
) {
    val email = sharePreferenceViewModel.email.collectAsState("")
    val userName = sharePreferenceViewModel.userName.collectAsState("")
    val userImage = sharePreferenceViewModel.userImage.collectAsState("")

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        HeaderHomeInfo(
            imageUri = userImage.value,
            userName = userName.value,
            email = email.value
        )
    }) { paddingValues ->
        WavesBackground(modifier = Modifier.padding(paddingValues)) {
            PagingItems(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp),
                homeViewModel = homeViewModel,
                onClickShowMovie = {
                    navigateToMovies(it)
                }
            )
        }
    }
}


@Composable
fun PagingItems(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    onClickShowMovie: (GamesModel) -> Unit
) {
    val lazyPagingItems = homeViewModel.gamePage.collectAsLazyPagingItems()
    val lazyListState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = modifier,
        state = lazyListState,
        columns = GridCells.Fixed(3),
    ) {
        if (lazyPagingItems.itemCount > 0) {
            items(lazyPagingItems.itemCount) {
                val itemsPag = lazyPagingItems[it]
                if (itemsPag != null) {
                    GridItemHomeScreen(
                        itemsPag.backgroundImage,
                        itemsPag.gameName,
                        itemsPag.platforms,
                        onClickShowVideo = {
                            onClickShowMovie(itemsPag)
                        }
                    )
                }
            }

            when (lazyPagingItems.loadState.append) {
                LoadState.Loading -> {
                    if (lazyPagingItems.itemCount > 15) {
                        items(3) {
                            CardLessSkeleton()
                        }
                    }
                }

                is LoadState.Error,
                is LoadState.NotLoading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                            Text(
                                text = "La información no está disponible por el momento.;",
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                style = textLoader,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        } else {
            when (lazyPagingItems.loadState.refresh) {
                LoadState.Loading -> {
                    items(12) {
                        CardLessSkeleton()
                    }
                }

                is LoadState.Error,
                is LoadState.NotLoading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                            Text(
                                text = "La información no está disponible por el momento.",
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                style = textLoader,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}