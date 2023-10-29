package com.hopcape.routing.user

import com.hopcape.domain.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.userRoutes(tokenConfig: TokenConfig){
    routing {
        authenticate()
        signUp()
        signIn(tokenConfig)
    }
}