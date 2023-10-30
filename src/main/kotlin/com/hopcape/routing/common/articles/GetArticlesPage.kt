package com.hopcape.routing.common.articles

import com.hopcape.data.response.article.ArticlesResponse
import com.hopcape.domain.repository.ArticleRepository
import com.hopcape.routing.utils.RouterHelper.CommonRoutes.ARTICLES_PAGE
import com.hopcape.routing.utils.RouterHelper.Params.DEFAULT_ARTICLES_LIMIT_PER_PAGE
import com.hopcape.routing.utils.RouterHelper.Params.LIMIT_QUERY
import com.hopcape.routing.utils.RouterHelper.Params.PAGE_QUERY
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * @receiver [Routing]
 * Exposes a [get] endpoint at [ARTICLES_PAGE] which takes
 * as query [PAGE_QUERY] as page number and [LIMIT_QUERY] as the limit or
 * number or results per page
 * @return [ArticlesResponse] */
fun Routing.getArticlesPage(){
    val repository by inject<ArticleRepository>()
    authenticate {
        get(ARTICLES_PAGE){
            val pageNumber = call.parameters[PAGE_QUERY]?.toInt() ?: 0
            val limit = call.parameters[LIMIT_QUERY]?.toInt() ?: DEFAULT_ARTICLES_LIMIT_PER_PAGE

            val articles =
                repository.getPagedArticles(
                    offset = getPageOffsetFor(
                        pageNumber,
                        limit
                    ),
                    limit = limit
                )

            articles.ifEmpty {
                call.respond(HttpStatusCode.NoContent,"Articles Not found")
                return@get
            }

            call.respond(HttpStatusCode.OK,ArticlesResponse(articles.map { it.toArticleResponse() }))
        }
    }
}

/**
 * Calculates the page offset
 * @param pageNumber
 * @param limit
 * E.g if pageNumber is 2 an limit is 30 then the offset is going to be 60*/
private fun getPageOffsetFor(pageNumber: Int, limit: Int): Int{
    return pageNumber * limit
}