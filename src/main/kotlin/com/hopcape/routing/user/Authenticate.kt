package com.hopcape.routing.user

import com.hopcape.routing.utils.RouterHelper.UserRoutes.AUTHENTICATE_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.authenticate(){
    authenticate {
        get(AUTHENTICATE_ROUTE){
            call.respond(HttpStatusCode.OK)
        }
    }
}