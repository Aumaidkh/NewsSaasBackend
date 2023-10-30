package com.hopcape.routing.common

import com.hopcape.routing.common.users.articles.getAllArticles
import com.hopcape.routing.common.users.articles.getArticle
import com.hopcape.routing.common.users.articles.getArticlesPage
import com.hopcape.routing.common.users.user.updateUser
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