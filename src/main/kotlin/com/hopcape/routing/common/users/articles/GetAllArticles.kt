package com.hopcape.routing.common.users.articles

import com.hopcape.data.response.article.ArticlesResponse
import com.hopcape.domain.repository.ArticleRepository
import com.hopcape.routing.utils.RouterHelper.CommonRoutes.ALL_ARTICLES
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.getAllArticles(){
    val repository by inject<ArticleRepository>()
    authenticate {
        get(ALL_ARTICLES){

            val articles =
                repository.getAllArticles()

            articles.ifEmpty {
                call.respond(HttpStatusCode.NoContent,"No articles found")
                return@get
            }

            call.respond(HttpStatusCode.OK,ArticlesResponse(articles = articles.map { it.toArticleResponse() }))
        }
    }
}