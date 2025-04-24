package com.example.gamesshare

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.gamesshare.ui.theme.GamesShareTheme
import com.example.gamesshare.ui.view.navigation.NavGraphComposeView
import com.example.gamesshare.utils.loader.DsWidgetLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val sharePreferenceViewModel: SharePreferenceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamesShareTheme {
                NavGraphComposeView(sharePreferenceViewModel)
                DsWidgetLoader()
            }
        }
    }
}