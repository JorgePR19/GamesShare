package com.example.gamesshare.ui.view.screens.login.firsttime

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.customtextfield.widget.decorationBox.main.DsPasswordTextField
import com.example.customtextfield.widget.decorationBox.main.DsSimpleTextField
import com.example.customtextfield.widget.decorationBox.main.model.DsInputModel
import com.example.gamesshare.SharePreferenceViewModel
import com.example.gamesshare.ui.view.components.GenericButton
import com.example.gamesshare.ui.view.components.HeaderTextAndImage
import com.example.gamesshare.ui.view.components.TextWithArrow
import com.example.gamesshare.ui.view.components.WavesBackground
import com.example.gamesshare.ui.view.screens.login.LoginViewModel
import com.example.gamesshare.ui.view.screens.login.observer.LoginObserver
import com.example.gamesshare.utils.DimensDp.DP16
import com.example.gamesshare.utils.DimensDp.DP24
import com.example.gamesshare.utils.loader.DsLoaderView

@Composable
fun LoginFirstTimeComposeView(
    loginViewModel: LoginViewModel,
    navigateToHome: () -> Unit
) {
    WavesBackground {
        val focusManager: FocusManager = LocalFocusManager.current
        val stateLogin by loginViewModel.observerState.observeAsState()

        LoginObserver(stateLogin) {
            navigateToHome()
        }

        val passwordTextFocus: MutableState<FocusRequester> = remember {
            mutableStateOf(FocusRequester())
        }

        Column(
            modifier = Modifier
                .padding(DP16)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderTextAndImage(
                "Iniciar sesion",
                Modifier.windowInsetsPadding(WindowInsets.systemBars)
            )
            Spacer(modifier = Modifier.height(DP24))

            DsSimpleTextField(
                loginViewModel.loginEmailModel,
                onValueChange = { loginViewModel.onValueChangeLoginEmail(it) },
                onFocusChange = { loginViewModel.onFocusChangeLoginEmail(it) },
                keyBoardAction = { passwordTextFocus.value.requestFocus() },
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(DP16))

            DsPasswordTextField(
                loginViewModel.loginPasswordModel,
                onValueChange = { loginViewModel.onValueChangeLoginPw(it) },
                onFocusChange = { loginViewModel.onFocusChangeLoginPw(it) },
                keyBoardAction = { focusManager.clearFocus() },
                modifier = Modifier.focusRequester(passwordTextFocus.value)
            )
            Spacer(modifier = Modifier.height(DP24))

            GenericButton(title = "Entrar", isEnable = loginViewModel.activeButton()) {
                focusManager.clearFocus()
                loginViewModel.loginWithGmail()
            }
            Spacer(modifier = Modifier.weight(1f))
            TextWithArrow("Crear Cuenta")
        }
    }
}