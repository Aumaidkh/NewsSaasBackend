package com.hopcape.routing.admin.users

import com.hopcape.data.response.GetUsersResponse
import com.hopcape.domain.repository.UserRepository
import com.hopcape.routing.utils.RouterHelper.AdminRoutes.GET_ALL_USERS
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.getAllUsers(){
    val userRepository by inject<UserRepository>()
    authenticate {
        get(GET_ALL_USERS){
            val users =
                userRepository.getUsers()

            if (users.isEmpty()){
                call.respond(HttpStatusCode.NoContent,"No users found")
                return@get
            }

            call.respond(HttpStatusCode.OK,GetUsersResponse(users = users.map { it.toSerializableUser() }))
        }
    }
}