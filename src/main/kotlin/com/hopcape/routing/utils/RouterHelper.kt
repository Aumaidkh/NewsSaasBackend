package com.hopcape.routing.utils

object RouterHelper {

    object UserRoutes {
        const val SIGN_UP_ROUTE = "user/signup"
        const val SIGN_IN_ROUTE = "user/signin"
        const val AUTHENTICATE_ROUTE = "user/authenticate"

    }

    object Params{
        const val ARTICLE_ID = "articleId"
        const val ARTICLE_TITLE = "title"
        const val PAGE_QUERY = "page"
        const val LIMIT_QUERY = "limit"
        const val DEFAULT_ARTICLES_LIMIT_PER_PAGE = 1
    }

    object AdminRoutes {
        const val GET_ALL_USERS = "/admin/users"
        const val ADD_ARTICLE = "/admin/add-article"
        const val DELETE_ARTICLE = "/admin/article"
        const val UPDATE_ARTICLE = "/admin/article"
    }

    object CommonRoutes {
        const val ARTICLE_ROUTE = "/article"
        const val ALL_ARTICLES_ROUTE = "/articles"
        const val ARTICLES_PAGE = "/paged-articles"
    }
}