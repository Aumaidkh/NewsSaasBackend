package com.hopcape.data.response.comment

import kotlinx.serialization.Serializable

@Serializable
data class CommentsResponse(
    val comment: List<CommentResponse>
)
