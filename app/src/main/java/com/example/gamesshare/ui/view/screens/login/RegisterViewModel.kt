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
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.firebase.core.FirebaseLoginResponse
import com.example.gamesshare.domain.network.firebase.domain.login.FetchUserUserCase
import com.example.gamesshare.domain.network.firebase.domain.login.RegisterUseCase
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
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) :
    ViewModel() {
    /**
     * Regiter password
     */

    val defaultImageUri = "https://i.blogs.es/55321a/1366_2000/840_560.jpeg"
    var imageUri by mutableStateOf(defaultImageUri)

    var registerPasswordModel by mutableStateOf(
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

    fun onValueChangeRegisterPw(value: TextFieldValue) {
        registerPasswordModel = registerPasswordModel.copy(textValue = value)
    }

    fun onFocusChangeRegisterPw(value: Boolean) {
        registerPasswordModel =
            registerPasswordModel.copy(textFieldStatus = if (value) TextFieldStatus.EDITING else TextFieldStatus.ENABLE)
    }

    ///------ register email

    var haveFocusMoney by mutableStateOf(false)

    var registerEmailModel by mutableStateOf(
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

    fun onValueChangeRegisterEmail(value: TextFieldValue) {
        registerEmailModel = registerEmailModel.copy(textValue = value)
        changeStatusEmail(
            getValidationByType(
                ValidationsType.ValidEmail,
                null,
                registerEmailModel.textValue.text
            )
        )
    }

    fun onFocusChangeRegisterEmail(value: Boolean) {
        haveFocusMoney = value
        when (registerEmailModel.textFieldStatus) {
            TextFieldStatus.ENABLE, TextFieldStatus.SUCCESS, TextFieldStatus.EDITING -> {
                registerEmailModel =
                    registerEmailModel.copy(textFieldStatus = if (value) TextFieldStatus.EDITING else TextFieldStatus.ENABLE)
            }

            else -> Unit
        }
    }

    // User Name

    var registerUserNameModel by mutableStateOf(
        DsInputModel(
            textFieldStatus = TextFieldStatus.ENABLE,
            textValue = TextFieldValue(text = ""),
            label = "Nick name",
            helperText = "Nombre de usurio",
            dsTypesIcons = DsTypesIcons(
                startIcon = R.drawable.ic_user,
            ),
            properties = DsInputProperties(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                maxLength = 15,
                countLength = true
            )
        )
    )

    fun onValueChangeRegisterUser(value: TextFieldValue) {
        registerUserNameModel = registerUserNameModel.copy(textValue = value)
    }

    fun onFocusChangeRegisterUser(value: Boolean) {
        registerUserNameModel =
            registerUserNameModel.copy(textFieldStatus = if (value) TextFieldStatus.EDITING else TextFieldStatus.ENABLE)
    }

    // User Image

    var registerImageModel by mutableStateOf(
        DsInputModel(
            textFieldStatus = TextFieldStatus.ENABLE,
            textValue = TextFieldValue(text = ""),
            label = "Imagen URL",
            dsTypesIcons = DsTypesIcons(
                startIcon = R.drawable.ic_image,
                endIcon = R.drawable.ic_clear,
                successIcon = R.drawable.ic_clear,
            ),
            properties = DsInputProperties(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
    )

    fun onValueChangeRegisterImage(value: TextFieldValue) {
        registerImageModel = registerImageModel.copy(textValue = value)
        changeStatusImage(
            getValidationByType(
                ValidationsType.ValidUrl,
                null,
                registerImageModel.textValue.text
            )
        )
    }

    fun onFocusChangeRegisterImage(value: Boolean) {
        when (registerImageModel.textFieldStatus) {
            TextFieldStatus.ENABLE, TextFieldStatus.SUCCESS, TextFieldStatus.EDITING -> {
                registerImageModel =
                    registerImageModel.copy(textFieldStatus = if (value) TextFieldStatus.EDITING else TextFieldStatus.ENABLE)
            }

            else -> Unit
        }
    }


    /**
     * Validations
     */
    var numbers by mutableStateOf(false)
    var upper by mutableStateOf(false)
    var specialCharacter by mutableStateOf(false)
    var threeConsecutive by mutableStateOf(false)
    var isEmailValid by mutableStateOf(false)

    fun requestValidations() {
        numbers = validateInput(ValidationsType.ValidateNumbers)
        upper = validateInput(ValidationsType.ValidateUppercase)
        specialCharacter = validateInput(ValidationsType.ValidateSpecialCharacter)
        threeConsecutive = validateInput(ValidationsType.ValidateThreeNumbersInSequence)
    }

    private fun validateInput(
        validationsType: ValidationsType,
    ): Boolean {
        return getValidationByType(
            validationsType,
            registerPasswordModel.properties,
            registerPasswordModel.textValue.text
        )
    }

    fun activeButton() =
        numbers && upper && specialCharacter && threeConsecutive && isEmailValid && registerUserNameModel.textValue.text.isNotEmpty()

    private fun changeStatusEmail(validation: Boolean) {
        isEmailValid = validation
        if (validation) {
            registerEmailModel = registerEmailModel.copy(
                textFieldStatus = TextFieldStatus.SUCCESS
            )
        } else {
            registerEmailModel = registerEmailModel.copy(
                textFieldStatus = TextFieldStatus.ERROR
            )
        }
    }

    fun changeStatusImage(validation: Boolean) {
        if (validation) {
            registerImageModel = registerImageModel.copy(
                textFieldStatus = TextFieldStatus.SUCCESS
            )
            imageUri = registerImageModel.textValue.text
        } else {
            registerImageModel = registerImageModel.copy(
                textFieldStatus = TextFieldStatus.EDITING
            )
            imageUri = defaultImageUri
        }
    }

    //Register

    private val _observerState = MutableLiveData<ResponseStatus<FirebaseLoginResponse>?>()
    val observerState: LiveData<ResponseStatus<FirebaseLoginResponse>?> = _observerState

    fun registerUser() {
        _observerState.value = ResponseStatus.loading()
        viewModelScope.launch {
            _observerState.value = registerUseCase(
                email = registerEmailModel.textValue.text,
                password = registerPasswordModel.textValue.text,
                username = registerUserNameModel.textValue.text,
                userImage = imageUri
            )
        }
    }

    fun clearObserver() {
        _observerState.value = null
    }
}