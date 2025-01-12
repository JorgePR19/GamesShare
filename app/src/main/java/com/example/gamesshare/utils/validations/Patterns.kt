package com.example.gamesshare.utils.validations

class Patterns {
    companion object {
        const val validateNumbers: String = "1234567890"
        const val validateMoney: String = "1234567890$,."
        const val validLowercase: String = "abcdefghijklmnopqrstuvwxyz"
        const val validateUppercase: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        const val validateSpecialCharacter: String = "¡!@#\$%^&*/?¿()\\[\\]_`~;:+=ÁÉÍÓÚÀÈÌÒÙáéíóúàèìòù"

        //Regex
        const val regexNumbers :String = ".*[0-9].*"
        const val regexLowercase :String = ".*[a-z&&[^ñ]].*"
        const val regexUppercase :String = ".*[A-Z&&[^Ñ]].*"
        const val regexSpecialCharacter :String = ".*[¡!@#\$%^&*/?¿()\\[\\]_`~;:+=].*"
        const val regexValidateKeyBoardN :String = ".*[ñÑ].*"
        const val IMAGE_URL_REGEX =  "^(http|https)://.*$"
    }
}