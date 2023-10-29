package com.hopcape.plugins

import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.routing.userRoutes
import io.ktor.server.application.*

fun Application.configureRouting(tokenConfig: TokenConfig) {
    userRoutes(tokenConfig = tokenConfig)
}
