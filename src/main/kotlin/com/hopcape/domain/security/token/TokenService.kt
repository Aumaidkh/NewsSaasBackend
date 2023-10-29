package com.hopcape.domain.security.token

/**
 * Responsible for creating or generating tokens
 * */
interface TokenService {

    /**
     * Generates a token with
     * @param config
     * Also attaches claims
     * @param claim if needed
     * @return [String] which is the token created*/
    fun generate(
        config: TokenConfig,
        vararg claim: TokenClaim
    ): String

    companion object {
        // 1 Year
        const val DEFAULT_TOKEN_EXPIRATION = 1000L * 60L * 60L * 24L * 365L
    }

}