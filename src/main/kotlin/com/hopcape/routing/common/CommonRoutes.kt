package com.hopcape.routing.common

import com.hopcape.routing.common.users.articles.getAllArticles
import com.hopcape.routing.common.users.articles.getArticle
import com.hopcape.routing.common.users.articles.getArticlesPage
import com.hopcape.routing.common.users.user.updateUser
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.commonRoutes(){
    routing {
        updateUser()
        getArticle()
        getAllArticles()
        getArticlesPage()
    }
}