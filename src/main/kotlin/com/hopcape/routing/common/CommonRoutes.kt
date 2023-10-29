package com.hopcape.routing.common

import com.hopcape.routing.common.users.updateUser
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.commonRoutes(){
    routing {
        updateUser()
    }
}