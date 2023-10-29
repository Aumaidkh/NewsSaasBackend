package com.hopcape.routing.common.users

import com.hopcape.data.request.user.UpdateUserRequest
import com.hopcape.domain.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.updateUser(){
    val repository by inject<UserRepository>()
    authenticate{
        post("/user/update"){
            val userId = call.parameters["id"]
            if (userId == null){
                call.respond(HttpStatusCode.NotFound)
                return@post
            }
            val request = runCatching { call.receiveNullable<UpdateUserRequest?>() }.getOrNull() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Invalid Request, Please check all the parameters"
                )
                return@post
            }

            val user =
                repository.getUserById(userId)

            if (user == null){
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = "User not Found"
                )
                return@post
            }

            val userUpdated = repository.updateUser(
                user = user.copy(
                    fullName = request.fullName,
                    phoneNumber = request.phoneNumber,
                    profilePic = request.profilePic
                )
            )

            if (!userUpdated){
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = "Something went wrong while updating the user"
                )
                return@post
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = user
            )
        }
    }
}