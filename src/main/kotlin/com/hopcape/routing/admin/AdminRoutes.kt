package com.hopcape.routing.admin

import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.routing.admin.users.deleteUser
import com.hopcape.routing.admin.users.getAllUsers
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.adminRoutes(tokenConfig: TokenConfig){
    routing {
        getAllUsers()
        deleteUser()
    }
}