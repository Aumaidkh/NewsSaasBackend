package com.hopcape.routing.admin.articles

import com.hopcape.domain.repository.ArticleRepository
import com.hopcape.routing.utils.RouterHelper.AdminRoutes.DELETE_ARTICLE
import com.hopcape.routing.utils.RouterHelper.Params.ARTICLE_ID
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * Deletes an article to the application. This route is protected and requires authentication.
 *
 * @receiver The [Routing] instance to which this route is added.
 */
fun Routing.deleteArticle(){
    val repository by inject<ArticleRepository>()
    authenticate {
        delete(DELETE_ARTICLE) {
            val articleId = call.parameters[ARTICLE_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }

            val article =
                repository.getArticleById(articleId) ?: kotlin.run {
                    call.respond(HttpStatusCode.NoContent)
                    return@delete
                }

            val deleted =
                repository.deleteArticle(article)

            if (!deleted){
                call.respond(HttpStatusCode.InternalServerError)
                return@delete
            }

            call.respond(HttpStatusCode.OK,"Article Deleted")
            return@delete
        }
    }
}

