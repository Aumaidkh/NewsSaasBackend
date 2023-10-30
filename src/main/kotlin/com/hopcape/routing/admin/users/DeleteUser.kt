package com.hopcape.routing.admin.users

import com.hopcape.domain.repository.UserRepository
import com.hopcape.routing.utils.RouterHelper.AdminRoutes.DELETE_USER
import com.hopcape.routing.utils.RouterHelper.Params.EMAIL_QUERY
import com.hopcape.routing.utils.RouterHelper.Params.ID_QUERY
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * @receiver [Routing]
 * exposes an endpoint to delete a user
 * The request should have an [EMAIL_QUERY] param or [ID_QUERY]
 * attached to it*/
fun Routing.deleteUser(){
    val userRepository by inject<UserRepository>()
    delete(DELETE_USER){
        val containsEmail = call.parameters.contains(EMAIL_QUERY)
        val containsId = call.parameters.contains(ID_QUERY)

        // Get Hold of user
        // Either by email or by Id
        val user = if (containsEmail){
            val email = call.parameters[EMAIL_QUERY]
            if (email == null){
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }
            userRepository.getUserByEmail(email)
        } else {
            val id = call.parameters[ID_QUERY]
            if (id == null){
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }
            userRepository.getUserById(id)
        }

        // If user is not found
        if (user == null){
            call.respond(HttpStatusCode.NotFound,"User not found")
            return@delete
        }

        val deleted = userRepository.deleteUser(user)

        if (!deleted){
            call.respond(HttpStatusCode.NoContent,"Can't Delete User")
            return@delete
        }

        call.respond(HttpStatusCode.OK,"User deleted")
    }
}