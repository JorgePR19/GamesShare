package com.example.gamesshare.ui.view.screens.detail.views.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gamesshare.SharePreferenceViewModel
import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.network.StatusClass
import com.example.gamesshare.ui.theme.roboto14Regular
import com.example.gamesshare.ui.theme.rubik20sp
import com.example.gamesshare.ui.view.components.GenericButton
import com.example.gamesshare.ui.view.components.HeaderDetail
import com.example.gamesshare.ui.view.components.MainImage
import com.example.gamesshare.ui.view.components.WavesBackground
import com.example.gamesshare.ui.view.screens.detail.observer.GetGamesObserver
import com.example.gamesshare.ui.view.screens.detail.observer.GetMoviesObserver
import com.example.gamesshare.ui.view.screens.detail.observer.StatusUI
import com.example.gamesshare.utils.loader.DsLoaderView

@Composable
fun DetailGameScreen(
    viewModel: DetailGameViewModel = hiltViewModel(),
    sharePreferenceViewModel: SharePreferenceViewModel,
    game: GamesModel,
    navController: NavController,
    listMovies: () -> Unit
) {
    BackHandler { }

    val state by viewModel.observerData.collectAsState()
    val stateMovie by viewModel.observerState.collectAsState()

    val stateUI by viewModel.stateUI

    LaunchedEffect(key1 = state.status) {
        if (state.status is StatusClass.Loading) {
            DsLoaderView.showLoader()
            viewModel.fetchData(game.gameId)
        }
    }


    LaunchedEffect(key1 = stateMovie.status) {
        if (stateMovie.status is StatusClass.Loading) {
            viewModel.fetchMovies(game.slug)
        }
    }

    GetGamesObserver(state = state) {
        viewModel.setUIState(it)
    }
    GetMoviesObserver(state = stateMovie)

    WavesBackground {
        when (stateUI) {
            StatusUI.Await -> Unit
            StatusUI.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Error Al cargar los datos",
                        style = rubik20sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    GenericButton("Reintentar", true) {
                        viewModel.resetFetchData()
                    }
                }
            }
            StatusUI.Success -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    HeaderDetail(game.gameName, navController = navController)
                    ConstraintLayout(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(0x8AAFAFAF))
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 24.dp)
                    ) {
                        val (image, detail, iconVideo) = createRefs()

                        MainImage(
                            image = game.backgroundImage,
                            modifier = Modifier.constrainAs(image) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            }
                        )

                        if (!stateMovie.data.isNullOrEmpty()) {
                            IconButton(onClick = {
                                sharePreferenceViewModel.listMovies = stateMovie.data!!
                                listMovies()
                            }, modifier = Modifier.constrainAs(iconVideo) {
                                end.linkTo(image.end, margin = 16.dp)
                                bottom.linkTo(image.bottom)
                                top.linkTo(image.bottom)
                            }, colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.White,
                                contentColor = Color.DarkGray
                            )
                            ) {
                                Icon(imageVector = Icons.Outlined.PlayArrow, null)
                            }
                        }


                        // Caja desplazable que ocupa todo el espacio disponible después de la imagen
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))// Ancho completo
                                .constrainAs(detail) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    top.linkTo(
                                        image.bottom,
                                        margin = 24.dp
                                    ) // Empieza justo debajo de la imagen
                                    bottom.linkTo(parent.bottom) // Llega hasta el final del ConstraintLayout
                                    height =
                                        Dimension.fillToConstraints // Ajusta la altura al espacio disponible
                                }
                                .verticalScroll(rememberScrollState()) // Habilita el scroll
                                .background(Color(0x802F2A2A))
                        ) {
                            Text(
                                text = state.data!!.description,
                                textAlign = TextAlign.Justify,
                                color = Color.White,
                                style = roboto14Regular,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}