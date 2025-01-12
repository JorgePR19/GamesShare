package com.example.gamesshare.ui.view.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gamesshare.SharePreferenceViewModel
import com.example.gamesshare.ui.view.components.HeaderHomeInfo
import com.example.gamesshare.ui.view.components.WavesBackground

@Composable
fun HomeScreen(sharePreferenceViewModel: SharePreferenceViewModel) {
    val email = sharePreferenceViewModel.email.collectAsState("")
    val userName = sharePreferenceViewModel.userName.collectAsState("")
    val userImage = sharePreferenceViewModel.userImage.collectAsState("")

    WavesBackground {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderHomeInfo(imageUri = userImage.value, userName = userName.value, email = email.value)
        }
    }
}