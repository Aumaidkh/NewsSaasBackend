package com.hopcape.routing.common.comment

import com.hopcape.data.response.ErrorResponse
import com.hopcape.data.response.SuccessResponse
import com.hopcape.data.response.comment.CommentsResponse
import com.hopcape.domain.comment.CommentRepository
import com.hopcape.routing.utils.RouterHelper.CommonRoutes.COMMENT_ROUTE
import com.hopcape.routing.utils.RouterHelper.Params.ID_QUERY
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * @receiver [Routing]
 * Exposes a [delete] method on [COMMENT_ROUTE]
 * accepting [ID_QUERY] as query parameter
 * @return [CommentsResponse] with the comment that was deleted
 * specified in [ID_QUERY]*/
fun Routing.deleteComment(){
    val repository by inject<CommentRepository>()
    authenticate {
        delete(COMMENT_ROUTE){
            // get hold of the comment id
            val commentId =
                call.parameters[ID_QUERY]


            commentId?.let {
                // Get Hold of the comment
                val comment =
                    repository.getCommentById(it)

                if (comment == null){
                    call.respond(HttpStatusCode.NotFound,ErrorResponse("Comment with id $commentId not found"))
                    return@delete
                }

                val deleted =
                    repository.deleteComment(comment)

                if (!deleted){
                    call.respond(HttpStatusCode.InternalServerError,ErrorResponse("Something went wrong while deleting comment"))
                    return@delete
                }

                call.respond(HttpStatusCode.OK,SuccessResponse(comment.toCommentResponse()))
            } ?: run {
                call.respond(HttpStatusCode.NotFound,ErrorResponse("Url not found"))
            }
        }
    }
}