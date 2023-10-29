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

fun Routing.deleteUser(){
    val userRepository by inject<UserRepository>()
    delete(DELETE_USER){
        val containsEmail = call.parameters.contains(EMAIL_QUERY)
        val containsId = call.parameters.contains(ID_QUERY)
        val deleted = if (containsEmail){
            val email = call.parameters[EMAIL_QUERY]
            if (email == null){
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }
            userRepository.deleteUser(by = UserRepository.Operation.DeleteBy.Email(email))
        } else if (containsId) {
            val id = call.parameters[ID_QUERY]
            if (id == null){
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }
            userRepository.deleteUser(by = UserRepository.Operation.DeleteBy.Id(id))
        } else {
            false
        }

        if (!deleted){
            call.respond(HttpStatusCode.NoContent,"Can't Delete User")
            return@delete
        }

        call.respond(HttpStatusCode.OK,"User deleted")
    }
}