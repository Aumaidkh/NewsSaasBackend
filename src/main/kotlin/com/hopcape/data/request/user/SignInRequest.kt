package com.hopcape.data.request.user

import kotlinx.serialization.Serializable

/**
 * Wrapper around the body of
 * Sign in request*/
@Serializable
data class SignInRequest(
    val email: String,
    val password: String
)
