package com.hopcape.routing.admin.articles

import com.hopcape.data.response.article.ArticleResponse
import com.hopcape.domain.repository.ArticleRepository
import com.hopcape.routing.utils.RouterHelper.CommonRoutes.ARTICLE_ROUTE
import com.hopcape.routing.utils.RouterHelper.Params.ARTICLE_ID
import com.hopcape.routing.utils.RouterHelper.Params.ARTICLE_TITLE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.getArticle(){
    val repository by inject<ArticleRepository>()
    authenticate {
        get(ARTICLE_ROUTE) {
            val hasArticleId = call.parameters.contains(ARTICLE_ID)
            val hasArticleTitle = call.parameters.contains(ARTICLE_TITLE)

            val article =
                if (hasArticleId){
                    repository.getArticleById(call.parameters[ARTICLE_ID]!!) ?: kotlin.run {
                        call.respond(HttpStatusCode.NotFound,"Can't find article with id")
                        return@get
                    }
                } else if (hasArticleTitle){
                    repository.getArticleByQuery(call.parameters[ARTICLE_TITLE]!!) ?: kotlin.run {
                        call.respond(HttpStatusCode.NotFound,"Can't find article with id")
                        return@get
                    }
                } else {
                    call.respond(HttpStatusCode.NotFound,"Can't find article with id")
                    return@get
                }

            call.respond(HttpStatusCode.OK, article.toArticleResponse())
        }

    }
}