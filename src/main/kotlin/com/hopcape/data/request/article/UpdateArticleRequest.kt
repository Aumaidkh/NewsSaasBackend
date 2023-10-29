package com.hopcape.data.request.article

import kotlinx.serialization.Serializable

@Serializable
data class UpdateArticleRequest(
    val id: String,
    val title: String? = null,
    val subtitle: String? = null,
    val date: Double? =  null,
    val thumbnail: String? = null,
    val category: String? = null,
    val content: String? = null,
    val sponsored: Boolean = false
)
