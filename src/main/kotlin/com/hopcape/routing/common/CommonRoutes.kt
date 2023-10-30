package com.hopcape.routing.common

import com.hopcape.routing.common.articles.getAllArticles
import com.hopcape.routing.common.articles.getArticle
import com.hopcape.routing.common.articles.getArticlesPage
import com.hopcape.routing.common.user.updateUser
import io.ktor.server.application.*
import io.ktor.server.routing.*


/**
 * Plugs in all the common routes that are to be used
 * by both admin and user*/
fun Application.commonRoutes(){
    routing {
        updateUser()
        getArticle()
        getAllArticles()
        getArticlesPage()
    }
}