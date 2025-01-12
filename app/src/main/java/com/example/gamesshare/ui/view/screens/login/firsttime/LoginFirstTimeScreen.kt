package com.example.gamesshare.ui.view.screens.login.firsttime

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.customtextfield.widget.decorationBox.main.model.DsInputModel
import com.example.gamesshare.ui.view.components.IndicationPage
import com.example.gamesshare.ui.view.screens.login.LoginViewModel
import com.example.gamesshare.ui.view.screens.login.RegisterViewModel
import com.example.gamesshare.utils.DimensDp.DP8
import kotlin.math.absoluteValue


@Composable
fun LoginFirstTimeScreen(navigateToHome: () -> Unit) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val registerViewModel: RegisterViewModel = hiltViewModel()

    val pagerState = rememberPagerState(pageCount = {
        2
    })
    HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) { page: Int ->
        Box(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            when (page) {
                0 -> {
                    registerViewModel.clearObserver()
                    LoginFirstTimeComposeView(loginViewModel) {
                        navigateToHome()
                    }
                }

                1 -> {
                    loginViewModel.clearObserver()
                    RegisterComposeView(registerViewModel){
                        navigateToHome()
                    }
                }
            }

            IndicationPage(
                pagerState.pageCount,
                pagerState.currentPage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = DP8)
            )
        }
    }
}