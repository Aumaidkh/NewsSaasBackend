package com.hopcape.routing.user.users

import com.hopcape.routing.utils.RouterHelper.UserRoutes.AUTHENTICATE_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * @receiver [Routing]
 * Exposes a [get] endpoint at [AUTHENTICATE_ROUTE]
 * @return [HttpStatusCode.OK] when user is authenticated else [HttpStatusCode.Unauthorized]*/
fun Routing.authenticate(){
    authenticate {
        get(AUTHENTICATE_ROUTE){
            call.respond(HttpStatusCode.OK)
        }
    }
}