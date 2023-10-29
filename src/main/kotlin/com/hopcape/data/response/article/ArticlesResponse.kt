package com.hopcape.data.response.article

import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponse(
    val articles: List<ArticleResponse>
)