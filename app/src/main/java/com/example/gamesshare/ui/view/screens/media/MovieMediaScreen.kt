package com.example.gamesshare.ui.view.screens.media

import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.gamesshare.ui.view.components.ComposableLifeCycle
import com.example.gamesshare.ui.view.components.WavesBackground


@OptIn(UnstableApi::class)
@Composable
fun HomeMovieMediaScreen(uri: String, viewModel: MovieMediaViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val enterFullScreen = { activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE }
    val exitFullScreen = { activity.requestedOrientation = SCREEN_ORIENTATION_USER }

    val player = remember { mutableStateOf(viewModel.player) }

    DisposableEffect(key1 = Unit) {
        activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE
        viewModel.initPlayer(
            context,
            uri
        )
        player.value = viewModel.player

        onDispose {
            viewModel.releasePlayer()
        }
    }

    val playerView = remember {
        PlayerView(context).apply {
            this.player = player.value
            controllerAutoShow = true
            keepScreenOn = true
            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
            /*setFullscreenButtonClickListener {
                if (it) enterFullScreen() else exitFullScreen()
            }*/
        }
    }

    DisposableEffect(key1 = player.value) {
        playerView.player = player.value
        onDispose {
            playerView.player = null
        }
    }

    ComposableLifeCycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> playerView.onResume()
            Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> playerView.onPause()
            else -> {}
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.systemBars
            )
            .background(Color.Black)
    ) {
        val (video, back) = createRefs()

        AndroidView(
            factory = {
                playerView.apply {
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            },
            modifier = Modifier.constrainAs(video) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

        )


        IconButton(onClick = {
            (context as MediaActivity).finish()
        }, modifier = Modifier.constrainAs(back) {
            start.linkTo(video.start, margin = 16.dp)
            top.linkTo(video.top)
        }, colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color(0x80CDCDCD),
            contentColor = Color.DarkGray
        )
        ) {
            Icon(imageVector = Icons.Outlined.ArrowBack, null)
        }
    }
}
