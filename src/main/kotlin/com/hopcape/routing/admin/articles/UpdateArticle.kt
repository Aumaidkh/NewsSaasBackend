package com.hopcape.routing.admin.articles

import com.hopcape.data.article.ArticleModel
import com.hopcape.data.request.article.UpdateArticleRequest
import com.hopcape.domain.repository.ArticleRepository
import com.hopcape.routing.utils.RouterHelper.AdminRoutes.ARTICLE_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
/**
 * Updates an existing article in the application. This route is protected and requires authentication.
 *
 * @receiver The [Routing] instance to which this route is added.
 */
fun Routing.updateArticle() {
    val repository by inject<ArticleRepository>()

    // Authentication is required to access this route
    authenticate {
        put(ARTICLE_ROUTE) {
            // Attempt to receive and parse an 'UpdateArticleRequest' from the incoming request
            val request = runCatching { call.receiveNullable<UpdateArticleRequest?>() }.getOrNull() ?: kotlin.run {
                // Respond with a Bad Request status and an error message if the request is invalid
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Invalid Request, Please check all the parameters"
                )
                return@put
            }

            // Retrieve the existing article from the repository based on the provided ID
            val article = repository.getArticleById(request.id)

            // Check if the article with the specified ID exists
            if (article == null) {
                // Respond with a Not Found status and an error message if the article is not found
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = "Article Not Found"
                )
                return@put
            }

            // Create a new article with updated fields, preserving the existing ID and 'val' fields
            val updatedArticle = ArticleModel(
                id = article.id,
                title = request.title ?: article.title,
                subtitle = request.subtitle ?: article.subtitle,
                category = request.category ?: article.category,
                content = request.content ?: article.content,
                thumbnail = request.thumbnail ?: article.thumbnail,
                sponsored = request.sponsored,
                date = request.date ?: article.date
            )

            // Update the article in the repository with the new article
            val updated = repository.updateArticle(updatedArticle)

            if (!updated) {
                // Respond with a Conflict status and an error message if the update is not successful
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = "Error updating article"
                )
                return@put
            }

            // Respond with a successful status and the updated article's response
            call.respond(
                status = HttpStatusCode.OK,
                message = updatedArticle.toArticleResponse()
            )
        }
    }
}
