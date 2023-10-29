package com.hopcape.domain.usecase.validation

import java.util.regex.Pattern


class EmailValidator{
    private val EMAIL_REGEX = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\$"
    operator fun invoke(value: String): ValidationResult{
        if (value.isBlank()){
            return ValidationResult(
                valid = false,
                message = "Email can't be empty"
            )
        }

        if (!Pattern.matches(EMAIL_REGEX,value)){
            return ValidationResult(
                valid = false,
                message = "Invalid email"
            )
        }

        return ValidationResult(
            valid = true
        )
    }
}