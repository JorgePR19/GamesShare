package com.example.gamesshare.ui.view.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.gamesshare.SharePreferenceViewModel
import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.network.StatusClass
import com.example.gamesshare.ui.view.screens.detail.views.detail.DetailGameScreen
import com.example.gamesshare.ui.view.screens.detail.views.listMovies.ListMoviesScreen
import com.example.gamesshare.ui.view.screens.home.HomeScreen
import com.example.gamesshare.ui.view.screens.login.firsttime.LoginFirstTimeScreen
import com.example.gamesshare.ui.view.screens.media.MediaActivity
import com.example.gamesshare.utils.loader.DsLoaderView
import kotlin.reflect.typeOf

@Composable
fun NavGraphComposeView(viewModel: SharePreferenceViewModel) {
    val navController = rememberNavController()
    val state by viewModel.state.observeAsState()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.getStartDestination()
    }

    if (state != null) {
        when (state!!.status) {
            is StatusClass.Error -> DsLoaderView.dismissLoader()
            is StatusClass.Loading -> DsLoaderView.showLoader()
            is StatusClass.Success -> {
                DsLoaderView.dismissLoader()
                NavHost(
                    navController = navController,
                    startDestination = state!!.data ?: LoginFirsTimeDestination
                ) {
                    composable<LoginFirsTimeDestination> {
                        LoginFirstTimeScreen {
                            viewModel.savaDataUser()
                            navController.navigate(HomeDestination) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }

                    composable<HomeDestination> {
                        HomeScreen(viewModel) {
                            navController.navigate(DetailDestination(it))
                        }
                    }

                    composable<DetailDestination>(typeMap = mapOf(typeOf<GamesModel>() to createNavType<GamesModel>())) {
                        val detail: DetailDestination = it.toRoute()
                        DetailGameScreen(
                            game = detail.detailModel,
                            sharePreferenceViewModel = viewModel,
                            navController = navController
                        ) {
                            navController.navigate(ListMoviesDestination)
                        }
                    }

                    composable<ListMoviesDestination> {
                        ListMoviesScreen(viewModel, navController = navController) {
                            val intent =Intent(context, MediaActivity::class.java)
                            intent.putExtra("URI", it)
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}