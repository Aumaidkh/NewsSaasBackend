package com.hopcape.routing.admin.articles

import com.hopcape.data.article.ArticleModel
import com.hopcape.data.request.article.AddArticleRequest
import com.hopcape.domain.repository.ArticleRepository
import com.hopcape.routing.utils.RouterHelper.AdminRoutes.ADD_ARTICLE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * Adds a new article to the application. This route is protected and requires authentication.
 *
 * @receiver The [Routing] instance to which this route is added.
 */
fun Routing.addArticle() {
    val repository by inject<ArticleRepository>()

    // Authentication is required to access this route
    authenticate {
        post(ADD_ARTICLE) {
            // Attempt to receive and parse an 'AddArticleRequest' from the incoming request
            val request = runCatching { call.receiveNullable<AddArticleRequest?>() }.getOrNull() ?: kotlin.run {
                // Respond with a Bad Request status and an error message if the request is invalid
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Invalid Request, Please check all the parameters"
                )
                return@post
            }

            // Create an 'ArticleModel' based on the received request
            val article = ArticleModel(
                title = request.title,
                subtitle = request.subtitle,
                date = request.date,
                thumbnail = request.thumbnail,
                content = request.content,
                sponsored = request.sponsored,
                category = request.category
            )

            // Insert the article into the repository and check if the insertion was successful
            val articleSaved = repository.insertArticle(article)

            if (!articleSaved) {
                // Respond with a Bad Request status and an error message if the article couldn't be saved
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error Saving article"
                )
                return@post
            }

            // Respond with a successful status and the article response
            call.respond(
                status = HttpStatusCode.OK,
                message = request.toArticleResponse()
            )
            return@post
        }
    }
}
