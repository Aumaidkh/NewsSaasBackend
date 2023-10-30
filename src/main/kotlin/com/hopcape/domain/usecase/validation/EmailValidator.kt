package com.hopcape.domain.usecase.validation

import com.hopcape.domain.repository.UserRepository
import java.util.regex.Pattern


class EmailValidator(private val userRepository: UserRepository? = null){
    private val EMAIL_REGEX = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\$"
    suspend operator fun invoke(value: String): ValidationResult{
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

        val existingUser =
            userRepository?.getUserByEmail(email = value)

        if (existingUser!=null){
            return ValidationResult(
                valid = false,
                message = "Email already associated with other account."
            )
        }

        return ValidationResult(
            valid = true
        )
    }
}