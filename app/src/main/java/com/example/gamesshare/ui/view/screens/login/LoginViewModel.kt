package com.example.gamesshare.ui.view.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customtextfield.widget.decorationBox.main.model.DsInputModel
import com.example.customtextfield.widget.decorationBox.main.model.DsInputProperties
import com.example.customtextfield.widget.decorationBox.main.model.DsTypesIcons
import com.example.customtextfield.widget.decorationBox.main.utils.TextFieldStatus
import com.example.gamesshare.R
import com.example.gamesshare.domain.local.DataStorePref
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.domain.network.firebase.domain.login.FetchUserUserCase
import com.example.gamesshare.domain.network.firebase.domain.login.LoginEmailUseCase
import com.example.gamesshare.utils.validations.Patterns.Companion.validLowercase
import com.example.gamesshare.utils.validations.Patterns.Companion.validateNumbers
import com.example.gamesshare.utils.validations.Patterns.Companion.validateSpecialCharacter
import com.example.gamesshare.utils.validations.Patterns.Companion.validateUppercase
import com.example.gamesshare.utils.validations.ValidationsType
import com.example.gamesshare.utils.validations.getValidationByType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithEmailUseCase: LoginEmailUseCase,
) : ViewModel() {
    private val  _observerState= MutableLiveData<ResponseStatus<FirebaseLoginResponse>?>()
    val observerState: LiveData<ResponseStatus<FirebaseLoginResponse>?> = _observerState

    var isEmailValid by mutableStateOf(false)

    var loginPasswordModel by mutableStateOf(
        DsInputModel(
            textValue = TextFieldValue(""),
            label = "Conmtraseña",
            dsTypesIcons = DsTypesIcons(
                startIcon = R.drawable.ic_lock,
            ),
            properties = DsInputProperties(
                maxLength = 18,
                keyBoardPermit = validateNumbers + validLowercase + validateUppercase + validateSpecialCharacter,
                imeAction = ImeAction.Done
            )
        )
    )

    fun onValueChangeLoginPw(value: TextFieldValue) {
        loginPasswordModel = loginPasswordModel.copy(textValue = value)
    }

    fun onFocusChangeLoginPw(value: Boolean) {
        loginPasswordModel =
            loginPasswordModel.copy(textFieldStatus = if (value) TextFieldStatus.EDITING else TextFieldStatus.ENABLE)
    }

    ///------ register email

    var haveFocusMoney by mutableStateOf(false)

    var loginEmailModel by mutableStateOf(
        DsInputModel(
            textFieldStatus = TextFieldStatus.ENABLE,
            textValue = TextFieldValue(text = ""),
            label = "Email",
            dsTypesIcons = DsTypesIcons(
                startIcon = R.drawable.ic_email,
                errorIcon = R.drawable.ic_error_icon,
                successIcon = R.drawable.ic_success_icon
            ),
            properties = DsInputProperties(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            )
        )
    )

    fun onValueChangeLoginEmail(value: TextFieldValue) {
        loginEmailModel = loginEmailModel.copy(textValue = value)
        changeStatusEmail(
            getValidationByType(
                ValidationsType.ValidEmail,
                null,
                loginEmailModel.textValue.text
            )
        )
    }

    fun onFocusChangeLoginEmail(value: Boolean) {
        haveFocusMoney = value
        when (loginEmailModel.textFieldStatus) {
            TextFieldStatus.ENABLE, TextFieldStatus.SUCCESS, TextFieldStatus.EDITING -> {
                loginEmailModel =
                    loginEmailModel.copy(textFieldStatus = if (value) TextFieldStatus.EDITING else TextFieldStatus.ENABLE)
            }

            else -> Unit
        }
    }

    private fun changeStatusEmail(validation: Boolean) {
        isEmailValid = validation
        if (validation) {
            loginEmailModel = loginEmailModel.copy(
                textFieldStatus = TextFieldStatus.SUCCESS
            )
        } else {
            loginEmailModel = loginEmailModel.copy(
                textFieldStatus = TextFieldStatus.ERROR
            )
        }
    }

    fun activeButton() = isEmailValid && loginPasswordModel.textValue.text.isNotEmpty()

    fun loginWithGmail() {
        _observerState.value = ResponseStatus.loading()
        viewModelScope.launch {
            _observerState.value = loginWithEmailUseCase(loginEmailModel.textValue.text, loginPasswordModel.textValue.text)
        }
    }

    fun clearObserver(){
        _observerState.value = null
    }
}