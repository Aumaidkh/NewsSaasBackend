package com.hopcape.domain.security.hashing

/**
 * @param hash - actual hash
 * @param salt - the salt that was applied*/
data class SaltedHash(
    val salt: String,
    val hash: String
)
