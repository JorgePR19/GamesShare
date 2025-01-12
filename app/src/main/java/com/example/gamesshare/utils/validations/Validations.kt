package com.example.gamesshare.utils.validations

import com.example.customtextfield.widget.decorationBox.main.model.DsInputProperties


sealed class ValidationsType {
    data object ValidLength : ValidationsType()
    data object ValidateNumbers : ValidationsType()
    data object ValidLowercase : ValidationsType()
    data object ValidateUppercase : ValidationsType()
    data object ValidateSpecialCharacter : ValidationsType()
    data object ValidateTwoNumbersConsecutively : ValidationsType()
    data object ValidateThreeNumbersInSequence : ValidationsType()
    data object ValidateKeyBoardN : ValidationsType()
    data object ValidateInRange : ValidationsType()
    data object ValidEmail : ValidationsType()
    data object ValidUrl : ValidationsType()
}

fun <T> getValidationByType(
    validationsType: ValidationsType,
    dsInputProperties: DsInputProperties? = null,
    data: T
): Boolean {
    return when (validationsType) {
        ValidationsType.ValidLength -> validateLength(dsInputProperties!!, data)
        ValidationsType.ValidateNumbers -> validateNumbers(data)
        ValidationsType.ValidLowercase -> validLowercase(data)
        ValidationsType.ValidateUppercase -> validateUppercase(data)
        ValidationsType.ValidateSpecialCharacter -> validateSpecialCharacter(data)
        ValidationsType.ValidateTwoNumbersConsecutively -> validateTwoNumbersConsecutively(data)
        ValidationsType.ValidateThreeNumbersInSequence -> validateThreeNumbersInSequence(data)
        ValidationsType.ValidateKeyBoardN -> validateKeyBoardN(data)
        ValidationsType.ValidateInRange -> validateInRange(properties = dsInputProperties!!, data)
        ValidationsType.ValidEmail -> validEmail(data)
        ValidationsType.ValidUrl -> validateUrl(data)
    }
}


private fun <T> validateUrl(textValue: T): Boolean {
    val text = textValue as String
    return validateRegex(text, Patterns.IMAGE_URL_REGEX)
}
private fun <T> validateLength(dsInputProperties: DsInputProperties, textValue: T): Boolean {
    val text = textValue as String
    return text.length <= dsInputProperties.maxLength
}

private fun <T> validateNumbers(textValue: T): Boolean {
    val text = textValue as String
    return validateRegex(text, Patterns.regexNumbers)
}

private fun <T> validLowercase(textValue: T): Boolean {
    val text = textValue as String
    return validateRegex(text, Patterns.regexLowercase)
}

private fun <T> validEmail(textValue: T): Boolean {
    val text = textValue as String
    return android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
}

private fun <T> validateUppercase(textValue: T): Boolean {
    val text = textValue as String
    return validateRegex(text, Patterns.regexUppercase)
}

private fun <T> validateSpecialCharacter(textValue: T): Boolean {
    val text = textValue as String
    return validateRegex(text, Patterns.regexSpecialCharacter) && text.isNotEmpty()
}

private fun <T> validateTwoNumbersConsecutively(textValue: T): Boolean {
    val text = textValue as String
    text.forEachIndexed { index, _ ->
        when (index) {
            in 0..text.length - 2 -> {
                if (text[index].isDigit() && text[index + 1].isDigit()) {
                    if (text[index].code == text[index + 1].code) {
                        return false
                    }
                }
            }
        }
    }
    return text.isNotEmpty() && text.matches(".*[0-9].*".toRegex())
}

private fun <T> validateThreeNumbersInSequence(textValue: T): Boolean {
    val text = textValue as String
    text.forEachIndexed { index, _ ->
        when (index) {
            in 1..text.length - 2 -> {
                if (text[index - 1].isDigit() && text[index].isDigit() && text[index + 1].isDigit()) {
                    if ((text[index].code - text[index - 1].code == 1) && (text[index + 1].code - text[index].code == 1)) {
                        return false
                    }
                }
            }
        }
    }
    return text.isNotEmpty() && text.matches(".*[0-9].*".toRegex())
}

private fun <T> validateKeyBoardN(textValue: T): Boolean {
    val text = textValue as String
    return !validateRegex(text, Patterns.regexValidateKeyBoardN) && text.isNotEmpty()
}

private fun <T> validateInRange(properties: DsInputProperties, textValue: T): Boolean {
    val text = textValue as Int
    return text in properties.minValue..properties.maxValue
}

private fun validateRegex(textValue: String, regex: String): Boolean =
    textValue.matches(regex.toRegex())