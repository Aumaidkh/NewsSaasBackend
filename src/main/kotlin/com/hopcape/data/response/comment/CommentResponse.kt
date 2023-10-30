package com.hopcape.data.response.comment

import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    val id: String = "",
    val addedAt: Long,
    val ownerId: String,
    val content: String,
    val contentId: String,
    val edited: Boolean = false,
)
