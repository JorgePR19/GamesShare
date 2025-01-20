package com.example.gamesshare.ui.view.screens.detail.views.listMovies

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gamesshare.SharePreferenceViewModel
import com.example.gamesshare.ui.view.components.HeaderDetail
import com.example.gamesshare.ui.view.components.ListMoviesItems
import com.example.gamesshare.ui.view.components.QualityMovies
import com.example.gamesshare.ui.view.components.WavesBackground

@Composable
fun ListMoviesScreen(
    sharePreferenceViewModel: SharePreferenceViewModel,
    navController: NavController,
    navigateToVideo: (String) -> Unit
) {
    BackHandler { }
    WavesBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderDetail("Trailers", navController)
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(sharePreferenceViewModel.listMovies) { game ->
                    ListMoviesItems(imageUri = game.preview, nameGame = game.nameGame) {
                        val uri = when (it) {
                            QualityMovies.High -> game.data.hideQuality
                            QualityMovies.Low -> game.data.lowQuality
                        }
                        navigateToVideo(uri)
                    }
                }
            }
        }
    }
}