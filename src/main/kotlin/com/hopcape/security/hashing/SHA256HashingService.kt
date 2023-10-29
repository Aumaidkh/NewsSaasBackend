package com.hopcape.security.hashing

import com.hopcape.domain.security.hashing.HashingService
import com.hopcape.domain.security.hashing.SaltedHash
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

/**
 * A [HashingService] which uses
 * [AlgorithmSHA256] for hashing*/
class SHA256HashingService: HashingService {

    companion object {
        const val SECURE_RANDOM_ALGORITHM = "SHA1PRNG"
    }

    override fun generateSaltedHash(value: String, saltLength: Int): SaltedHash {
        val salt = SecureRandom
            .getInstance(SECURE_RANDOM_ALGORITHM)
            .generateSeed(saltLength)

        val saltAsHex = Hex.encodeHexString(salt)

        val hash = DigestUtils.sha256Hex("$saltAsHex$value")

        return SaltedHash(
            salt = saltAsHex,
            hash = hash
        )
    }

    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        return DigestUtils.sha256Hex(saltedHash.salt+value) == saltedHash.hash
    }
}