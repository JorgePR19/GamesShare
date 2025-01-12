package com.example.gamesshare.utils.loader

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.gamesshare.R
import com.example.gamesshare.ui.theme.LoaderAlphaColor
import com.example.gamesshare.ui.theme.roboto16Medium
import com.example.gamesshare.ui.theme.textLoader


@Composable
fun DsWidgetLoader() {
    val showLoader by rememberSaveable {
        DsLoaderView.state
    }
    val isBlocking by rememberSaveable {
        DsLoaderView.isBlocking
    }
    BackHandler(enabled = isBlocking) {}

    if (showLoader) {
        val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
        val animationWidget by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1800),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LoaderAlphaColor)
                .zIndex(1f)
                .pointerInput(Unit) {
                    detectTapGestures { }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier.size(80.dp)
                )

                AnimatedVisibility(
                    visible = animationWidget >= 0.5f,
                    enter = expandHorizontally(animationSpec = tween(1000)),
                    exit = shrinkHorizontally(animationSpec = tween(1000)),
                ) {
                    Text(
                        text = "Loading....",
                        style = textLoader,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }
}