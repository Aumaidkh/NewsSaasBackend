package com.hopcape.data.response.article

import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    val title: String,
    val subtitle: String,
    val date: Long,
    val thumbnail: String,
    val category: String,
    val content: String,
    val sponsored: Boolean = false,
    val id: String = "",
)