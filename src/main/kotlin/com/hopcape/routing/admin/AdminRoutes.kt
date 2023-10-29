package com.hopcape.routing.admin

import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.routing.admin.articles.addArticle
import com.hopcape.routing.admin.articles.deleteArticle
import com.hopcape.routing.admin.articles.updateArticle
import com.hopcape.routing.admin.users.deleteUser
import com.hopcape.routing.admin.users.getAllUsers
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.adminRoutes(tokenConfig: TokenConfig){
    routing {
        // User Routes
        getAllUsers()
        deleteUser()

        // Article Routes
        addArticle()
        deleteArticle()
        updateArticle()
    }
}