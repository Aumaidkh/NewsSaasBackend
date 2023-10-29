package com.hopcape.domain.security.hashing

/**
 * Responsible for hashing*/
interface HashingService {

    /**
     * Generates the [SaltedHash] for
     * @param value
     * applies salt of length
     * @param saltLength while creating the hash and then
     * @return [SaltedHash] which contains info about the salt and hash*/
    fun generateSaltedHash(value: String,saltLength: Int = 32): SaltedHash

    /**
     * Verifies that
     * @param value when hashed with the same salt from the
     * @param saltedHash salt results in the same hash
     * as suggested by the saltedHash hash*/
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}