package com.hopcape.domain.security.token

/**
 * Wrapper around the configuration of the
 * [TokenService]*/
data class TokenConfig(
    val audience: String,
    val issuer: String,
    val secret: String,
    val expiresAt: Long
)
