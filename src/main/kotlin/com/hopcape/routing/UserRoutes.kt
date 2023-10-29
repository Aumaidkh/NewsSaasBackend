package com.hopcape.routing

import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.domain.security.token.TokenService
import com.hopcape.routing.admin.adminRoutes
import com.hopcape.routing.admin.users.deleteUser
import com.hopcape.routing.admin.users.getAllUsers
import com.hopcape.routing.common.commonRoutes
import com.hopcape.routing.user.authenticate
import com.hopcape.routing.user.signIn
import com.hopcape.routing.user.signUp
import com.hopcape.routing.user.userRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.userRoutes(tokenConfig: TokenConfig){
    userRoutes(tokenConfig)
    adminRoutes(tokenConfig)
    commonRoutes()
}
