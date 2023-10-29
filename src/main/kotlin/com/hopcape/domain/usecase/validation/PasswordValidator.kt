package com.hopcape.domain.usecase.validation

import java.util.regex.Pattern


class PasswordValidator {

    private val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$"
    private val MIN_PASSWORD_LENGTH = 8

    operator fun invoke(value: String): ValidationResult{
        if (value.isBlank()){
            return ValidationResult(
                valid = false,
                message = "Password can't be empty"
            )
        }

        if (value.length < MIN_PASSWORD_LENGTH){
            return ValidationResult(
                valid = false,
                message = "Password can't be less than $MIN_PASSWORD_LENGTH chars"
            )
        }

        if (!Pattern.matches(PASSWORD_REGEX,value)){
            return ValidationResult(
                valid = false,
                message = "Password must contain Minimum eight characters, at least one letter and one number"
            )
        }

        return ValidationResult(
            valid = true
        )
    }
}