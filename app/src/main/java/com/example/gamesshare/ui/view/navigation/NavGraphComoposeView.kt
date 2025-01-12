package com.example.gamesshare.ui.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamesshare.SharePreferenceViewModel
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.StatusClass
import com.example.gamesshare.ui.view.screens.home.HomeScreen
import com.example.gamesshare.ui.view.screens.login.firsttime.LoginFirstTimeScreen
import com.example.gamesshare.utils.loader.DsLoaderView

@Composable
fun NavGraphComposeView(viewModel: SharePreferenceViewModel){
    val navController = rememberNavController()
    val state by viewModel.state.observeAsState()


    LaunchedEffect(Unit) {
        viewModel.getStartDestination()
    }

    if(state != null){
        when(state!!.status){
            is StatusClass.Error -> DsLoaderView.dismissLoader()
            is StatusClass.Loading -> DsLoaderView.showLoader()
            is StatusClass.Success -> {
                DsLoaderView.dismissLoader()
                NavHost(
                    navController = navController,
                    startDestination = state!!.data ?: LoginFirsTimeDestination
                ){
                    composable<LoginFirsTimeDestination> {
                        LoginFirstTimeScreen{
                            viewModel.savaDataUser()
                            navController.navigate(HomeDestination){
                                popUpTo(navController.graph.startDestinationId){inclusive = true}
                                launchSingleTop = true
                            }
                        }
                    }

                    composable<HomeDestination> {
                        HomeScreen(viewModel)
                    }
                }
            }
        }
    }
}