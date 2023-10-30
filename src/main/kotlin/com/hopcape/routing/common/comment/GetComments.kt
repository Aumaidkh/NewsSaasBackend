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
 * Exposes a [get] method on [COMMENT_ROUTE]
 * accepting [ID_QUERY] as query parameter
 * @return [CommentsResponse] with list of comments for the post with id
 * specified in [ID_QUERY]*/
fun Routing.getComments(){
    val repository by inject<CommentRepository>()
    authenticate {
        get(COMMENT_ROUTE){
            // get hold of the comment id
            val contentId =
                call.parameters[ID_QUERY]

            contentId?.let {
                // Get Hold of the comment
                val comments =
                    repository.getCommentsForContent(it)

                comments.ifEmpty {
                    call.respond(HttpStatusCode.NotFound,ErrorResponse("No comments found for the post"))
                    return@get
                }

                call.respond(HttpStatusCode.OK,SuccessResponse(data = CommentsResponse(comments.map { comment -> comment.toCommentResponse() })))
            } ?: run {
                call.respond(HttpStatusCode.NotFound,ErrorResponse("Url not found"))
            }
        }
    }
}