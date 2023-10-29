package com.hopcape.data.request.article

import com.hopcape.data.response.article.ArticleResponse
import kotlinx.serialization.Serializable

@Serializable
data class AddArticleRequest(
    val title: String,
    val subtitle: String,
    val date: Double,
    val thumbnail: String,
    val category: String,
    val content: String,
    val sponsored: Boolean = false
) {

    fun toArticleResponse(id: String = "") = ArticleResponse(
        title, subtitle, date.toLong(), thumbnail, category, content, sponsored,id
    )
}
