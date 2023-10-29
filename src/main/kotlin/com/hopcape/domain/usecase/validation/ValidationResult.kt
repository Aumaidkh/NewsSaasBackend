package com.hopcape.domain.usecase.validation

/**
 * Wrapper around the validation result
 * @param valid - when true the result is valid
 * @param message - validation error message if any
 * */
data class ValidationResult(
    val valid: Boolean,
    val message: String? =  null
)
