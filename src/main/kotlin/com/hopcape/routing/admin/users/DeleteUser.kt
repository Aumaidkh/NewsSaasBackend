package com.hopcape.routing.admin.users

import com.hopcape.domain.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.deleteUser(){
    val userRepository by inject<UserRepository>()
    get("/admin/delete"){
        val containsEmail = call.parameters.contains("email")
        val containsId = call.parameters.contains("id")
        val deleted = if (containsEmail){
            val email = call.parameters["email"]
            if (email == null){
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            userRepository.deleteUser(by = UserRepository.Operation.DeleteBy.Email(email))
        } else if (containsId) {
            val id = call.parameters["id"]
            if (id == null){
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            userRepository.deleteUser(by = UserRepository.Operation.DeleteBy.Id(id))
        } else {
            false
        }

        if (!deleted){
            call.respond(HttpStatusCode.NoContent,"Can't Delete User")
            return@get
        }

        call.respond(HttpStatusCode.OK,"User deleted")
    }
}