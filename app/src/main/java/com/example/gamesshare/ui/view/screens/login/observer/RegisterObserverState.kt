package com.example.gamesshare.ui.view.screens.login.observer

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gamesshare.SharePreferenceViewModel
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.StatusClass
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.utils.loader.DsLoaderView

@Composable
fun RegisterObserverState(
    stateLogin: ResponseStatus<FirebaseLoginResponse>?,
    sharePreferenceViewModel: SharePreferenceViewModel = hiltViewModel(),
    onSuccessEvent: () -> Unit
) {
    val context= LocalContext.current

    if(stateLogin != null){
        when (stateLogin.status) {
            is StatusClass.Error -> {
                DsLoaderView.dismissLoader()
                Toast.makeText(context, stringResource(stateLogin.message!!), Toast.LENGTH_LONG).show()
            }

            is StatusClass.Loading -> {
                DsLoaderView.showLoader()
            }

            is StatusClass.Success -> {
                when (stateLogin.data) {
                    is FirebaseLoginResponse.FirebaseError -> {
                        DsLoaderView.dismissLoader()
                        Toast.makeText(context, stateLogin.data.messageId, Toast.LENGTH_LONG).show()
                    }

                    FirebaseLoginResponse.FirebaseSuccess -> {
                        DsLoaderView.dismissLoader()
                        sharePreferenceViewModel.setStarDestination(true)
                        onSuccessEvent()
                    }
                    else ->Unit
                }
            }
        }
    }
}
