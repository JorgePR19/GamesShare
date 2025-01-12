package com.example.gamesshare.ui.view.screens.login.firsttime

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.customtextfield.widget.decorationBox.main.DsPasswordTextField
import com.example.customtextfield.widget.decorationBox.main.DsSimpleTextField
import com.example.gamesshare.ui.view.components.CoilImage
import com.example.gamesshare.ui.view.components.GenericButton
import com.example.gamesshare.ui.view.components.HeaderTextAndImage
import com.example.gamesshare.ui.view.components.LabelsValidations
import com.example.gamesshare.ui.view.components.WavesBackground
import com.example.gamesshare.ui.view.screens.login.RegisterViewModel
import com.example.gamesshare.ui.view.screens.login.observer.RegisterObserverState
import com.example.gamesshare.utils.DimensDp.DP16
import com.example.gamesshare.utils.DimensDp.DP24
import com.example.gamesshare.utils.DimensDp.DP8

@Composable
fun RegisterComposeView(
    registerViewModel: RegisterViewModel,
    navigateToHome: () -> Unit
) {
    WavesBackground {
        val state by registerViewModel.observerState.observeAsState()
        RegisterObserverState(stateLogin = state) {
            navigateToHome()
        }

        val focusManager: FocusManager = LocalFocusManager.current
        registerViewModel.requestValidations()

        val passwordTextFocus: MutableState<FocusRequester> = remember {
            mutableStateOf(FocusRequester())
        }
        val emailTextFocus: MutableState<FocusRequester> = remember {
            mutableStateOf(FocusRequester())
        }
        val userNameTextFocus: MutableState<FocusRequester> = remember {
            mutableStateOf(FocusRequester())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(DP16)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderTextAndImage("Crear Cuenta", modifier = Modifier.padding(top = DP16))
            CoilImage(registerViewModel.imageUri, Modifier.size(120.dp))

            DsSimpleTextField(
                registerViewModel.registerImageModel,
                onValueChange = { registerViewModel.onValueChangeRegisterImage(it) },
                onFocusChange = { registerViewModel.onFocusChangeRegisterImage(it) },
                endIconAction = { registerViewModel.onValueChangeRegisterImage(TextFieldValue("")) },
                keyBoardAction = { userNameTextFocus.value.requestFocus() },
                modifier = Modifier.padding(top = DP8)
            )

            Spacer(modifier = Modifier.height(DP16))

            DsSimpleTextField(
                registerViewModel.registerUserNameModel,
                onValueChange = { registerViewModel.onValueChangeRegisterUser(it) },
                onFocusChange = { registerViewModel.onFocusChangeRegisterUser(it) },
                keyBoardAction = { emailTextFocus.value.requestFocus() },
                modifier = Modifier.focusRequester(userNameTextFocus.value)
            )

            Spacer(modifier = Modifier.height(DP8))

            DsSimpleTextField(
                registerViewModel.registerEmailModel,
                onValueChange = { registerViewModel.onValueChangeRegisterEmail(it) },
                onFocusChange = { registerViewModel.onFocusChangeRegisterEmail(it) },
                keyBoardAction = { passwordTextFocus.value.requestFocus() },
                modifier = Modifier.focusRequester(emailTextFocus.value)
            )

            Spacer(modifier = Modifier.height(DP8))

            DsPasswordTextField(
                registerViewModel.registerPasswordModel,
                onValueChange = { registerViewModel.onValueChangeRegisterPw(it) },
                onFocusChange = { registerViewModel.onFocusChangeRegisterPw(it) },
                keyBoardAction = { focusManager.clearFocus() },
                modifier = Modifier.focusRequester(passwordTextFocus.value)
            )

            Spacer(modifier = Modifier.height(DP16))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0x80FAFAFA))
            ) {
                LabelsValidations(registerViewModel)
            }

            Spacer(modifier = Modifier.height(DP24))

            GenericButton(title = "Entrar", isEnable = registerViewModel.activeButton()) {
                focusManager.clearFocus()
                registerViewModel.registerUser()
            }
        }
    }
}