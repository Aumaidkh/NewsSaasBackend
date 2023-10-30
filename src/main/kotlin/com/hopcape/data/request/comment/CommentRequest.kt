package com.hopcape.data.request.comment

import com.hopcape.data.comment.Comment
import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    val contentId: String,
    val userId: String,
    val content: String
) {

    fun toComment(): Comment {
        return Comment(
            ownerId = userId,
            content = content,
            contentId = contentId,
            addedAt = System.currentTimeMillis()
        )
    }
}
