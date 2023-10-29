package com.hopcape.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.hopcape.domain.security.token.TokenClaim
import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.domain.security.token.TokenService
import java.util.*

/**
 * A [TokenService] which
 * Uses JWT under the hood to create the tokens
 * */
class JwtTokenService: TokenService {

    override fun generate(config: TokenConfig, vararg claim: TokenClaim): String {
        var token = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + TokenService.DEFAULT_TOKEN_EXPIRATION))

        claim.forEach { tokenClaim ->
            token = token.withClaim(tokenClaim.name,tokenClaim.value)
        }

        return token.sign(Algorithm.HMAC256(config.secret))
    }
}