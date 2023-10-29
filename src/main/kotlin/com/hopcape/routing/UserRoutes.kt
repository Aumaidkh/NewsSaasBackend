package com.hopcape.routing

import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.routing.admin.adminRoutes
import com.hopcape.routing.common.commonRoutes
import com.hopcape.routing.user.userRoutes
import io.ktor.server.application.*

fun Application.userRoutes(tokenConfig: TokenConfig){
    userRoutes(tokenConfig)
    adminRoutes(tokenConfig)
    commonRoutes()
}
