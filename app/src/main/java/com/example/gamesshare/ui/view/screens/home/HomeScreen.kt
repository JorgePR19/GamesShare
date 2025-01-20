package com.example.gamesshare.ui.view.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
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
    navigateToMovies:(GamesModel)->Unit
) {
    val email = sharePreferenceViewModel.email.collectAsState("")
    val userName = sharePreferenceViewModel.userName.collectAsState("")
    val userImage = sharePreferenceViewModel.userImage.collectAsState("")

    WavesBackground {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderHomeInfo(
                imageUri = userImage.value,
                userName = userName.value,
                email = email.value
            )

            PagingItems(
                viewModel = homeViewModel,
                modifier = Modifier
                    .weight(1f).padding(top = 4.dp),
                onClickShowMovie = {
                    navigateToMovies(it)
                }
            )
        }
    }
}


@Composable
fun PagingItems(
    viewModel: HomeViewModel,
    modifier: Modifier,
    onClickShowMovie: (GamesModel) -> Unit
) {
    val lazyListState = rememberLazyGridState()
    val lazyPagingItems = viewModel.gamePage.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = modifier,
        state = lazyListState,
        columns = GridCells.Adaptive(minSize = 128.dp),
    ) {
        when {
            lazyPagingItems.loadState.isIdle || lazyPagingItems.loadState.append is LoadState.Loading -> {
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
                        is LoadState.Error -> {}

                        LoadState.Loading -> {
                            if (lazyPagingItems.itemCount > 15) {
                                items(3) {
                                    CardLessSkeleton()
                                }
                            }
                        }

                        is LoadState.NotLoading -> Unit
                    }
                } else {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                            Text(
                                text = "Error al cargar datos",
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

            lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                items(12) {
                    CardLessSkeleton()
                }
            }

            else -> {
                if (lazyPagingItems.itemCount > 15) {
                    items(3) {
                        CardLessSkeleton()
                    }
                }
            }
        }
    }
}