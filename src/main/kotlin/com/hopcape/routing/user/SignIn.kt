package com.hopcape.routing.user

import com.hopcape.data.request.user.SignInRequest
import com.hopcape.data.request.user.SignUpRequest
import com.hopcape.data.response.SignInResponse
import com.hopcape.domain.repository.UserRepository
import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.domain.usecase.validation.EmailValidator
import com.hopcape.domain.usecase.validation.PasswordValidator
import com.hopcape.routing.utils.RouterHelper.UserRoutes.SIGN_IN_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.signIn(
    tokenConfig: TokenConfig
){
    val userRepository by inject<UserRepository>()
    post(SIGN_IN_ROUTE) {
        val request = runCatching { call.receiveNullable<SignInRequest?>() }.getOrNull() ?: kotlin.run {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Invalid Request, Please check all the parameters"
            )
            return@post
        }

        // Validate email
        EmailValidator().invoke(request.email).also {
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

        try {
            val token =
                userRepository.authenticateUser(
                    email = request.email,
                    password = request.password,
                    config = tokenConfig
                )
            call.respond(
                status = HttpStatusCode.OK,
                message = SignInResponse(
                    token = token
                )
            )

        } catch (e: Exception){
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = e.message.toString()
            )
            return@post
        }
    }
}