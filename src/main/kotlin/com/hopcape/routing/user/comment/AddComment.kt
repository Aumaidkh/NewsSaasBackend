package com.hopcape.routing.user.comment

import com.hopcape.data.request.comment.CommentRequest
import com.hopcape.data.response.ErrorResponse
import com.hopcape.data.response.SuccessResponse
import com.hopcape.data.response.comment.CommentsResponse
import com.hopcape.domain.comment.CommentRepository
import com.hopcape.domain.repository.ArticleRepository
import com.hopcape.domain.usecase.validation.CommentValidator
import com.hopcape.routing.utils.RouterHelper.CommonRoutes.COMMENT_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * @receiver [Routing]
 * Exposes a [post] method on the [COMMENT_ROUTE] url
 * Accepting [CommentRequest] inside the body and
 * performs the validation using [CommentValidator]
 * @return [CommentsResponse] upon adding the comment
 * */
fun Routing.addComment(){
    val repository by inject<CommentRepository>()
    val validator by inject<CommentValidator>()
    authenticate {
        post(COMMENT_ROUTE){
            val request = runCatching { call.receiveNullable<CommentRequest?>() }.getOrNull() ?: run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(message = "Please ensure no fields are empty")
                )
                return@post
            }

            // Handle Validation
            val validationResult =
                validator.invoke(request)

            if (!validationResult.valid){
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(message = validationResult.message.toString())
                )
                return@post
            }

            // Add Comment
            val commentAdded =
                repository.insertComment(
                    comment = request.toComment()
                )

            if (!commentAdded){
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(message = "Something went wrong while adding comment")
                )
                return@post
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = SuccessResponse(data = request.toComment().toCommentResponse())
            )

        }
    }
}