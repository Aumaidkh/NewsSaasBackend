package com.hopcape.routing.common.users.user

import com.hopcape.data.request.user.UpdateUserRequest
import com.hopcape.data.user.User
import com.hopcape.domain.repository.UserRepository
import com.hopcape.routing.utils.RouterHelper.CommonRoutes.USER_ROUTE
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
        put(USER_ROUTE){
            val request = runCatching { call.receiveNullable<UpdateUserRequest?>() }.getOrNull() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Invalid Request, Please check all the parameters"
                )
                return@put
            }

            val user =
                repository.getUserById(request.id)

            if (user == null){
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = "User not Found"
                )
                return@put
            }

            val updatedUser = User(
                id = user.id,
                fullName = request.fullName ?: user.fullName,
                email = user.email,
                password = user.password,
                phoneNumber = request.phoneNumber ?: user.phoneNumber,
                profilePic = request.profilePic ?: user.profilePic
            )
            val userUpdated = repository.updateUser(user = updatedUser)

            if (!userUpdated){
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = "Something went wrong while updating the user"
                )
                return@put
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = user.toSerializableUser()
            )
        }
    }
}