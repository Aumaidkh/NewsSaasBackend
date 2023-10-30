package com.hopcape.routing.user.users

import com.hopcape.data.request.user.SignUpRequest
import com.hopcape.data.user.User
import com.hopcape.domain.repository.UserRepository
import com.hopcape.domain.usecase.validation.EmailValidator
import com.hopcape.domain.usecase.validation.PasswordValidator
import com.hopcape.routing.utils.RouterHelper
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


/**
 * @receiver [Routing]
 * exposes a [post] endpoint at [RouterHelper.CommonRoutes.USER_ROUTE]
 * and accepts [SignUpRequest] as body and signs up the user after performing
 * validation*/
fun Routing.signUp(){
    val userRepository by inject<UserRepository>()
    /**
     * This route*/
    post(RouterHelper.CommonRoutes.USER_ROUTE){
        val request = runCatching { call.receiveNullable<SignUpRequest?>() }.getOrNull() ?: kotlin.run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Invalid Request, Please check all the parameters"
            )
            return@post
        }

        // Validate email
        EmailValidator(userRepository).invoke(request.email).also {
            if (!it.valid){
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = it.message.toString()
                )
                return@post
            }
        }

        // Validate Password
        PasswordValidator().invoke(request.password).also {
            if (!it.valid){
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = it.message.toString()
                )
                return@post
            }
        }

        if (request.fullName.isBlank()){
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Full name can't be empty"
            )
            return@post
        }

        val userCreated =
            userRepository.createUser(user = User(
                email = request.email,
                password = request.password,
                phoneNumber = request.phoneNumber,
                profilePic = request.profilePic,
                fullName = request.fullName
            )
            )

        if (!userCreated){
            call.respond(
                status = HttpStatusCode.Conflict,
                message = "Something went wrong while creating the user"
            )
            return@post
        }

        call.respond(
            status = HttpStatusCode.OK,
            message = "Success"
        )
    }
}