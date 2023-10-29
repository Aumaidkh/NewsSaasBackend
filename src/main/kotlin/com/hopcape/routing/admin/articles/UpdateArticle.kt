package com.hopcape.routing.admin.articles

import com.hopcape.data.article.ArticleModel
import com.hopcape.data.request.article.UpdateArticleRequest
import com.hopcape.domain.repository.ArticleRepository
import com.hopcape.routing.utils.RouterHelper.AdminRoutes.ARTICLE_ROUTE
import com.hopcape.routing.utils.RouterHelper.AdminRoutes.UPDATE_ARTICLE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.updateArticle(){
    val repository by inject<ArticleRepository>()
    authenticate {
        put(ARTICLE_ROUTE){
            val request = runCatching { call.receiveNullable<UpdateArticleRequest?>() }.getOrNull() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Invalid Request, Please check all the parameters"
                )
                return@put
            }

            val article =
                repository.getArticleById(request.id)

            if (article == null){
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = "Article Not Found"
                )
                return@put
            }

            val updatedArticle = ArticleModel(
                id = article.id,  // Preserve the existing ID
                title = request.title ?: article.title,
                subtitle = request.subtitle ?: article.subtitle,
                category = request.category ?: article.category,
                content = request.content ?: article.content,
                thumbnail = request.thumbnail ?: article.thumbnail,
                sponsored = request.sponsored ?: article.sponsored,
                date = request.date ?: article.date
            )



            val updated =
                repository.updateArticle(
                    articleModel = updatedArticle
                )

            if (!updated){
                call.respond(
                    status = HttpStatusCode.Conflict,
                    message = "Error updating article"
                )
                return@put
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = updatedArticle.toArticleResponse()
            )
        }
    }
}