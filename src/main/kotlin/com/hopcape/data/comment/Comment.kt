package com.hopcape.data.comment

import com.hopcape.data.response.comment.CommentResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Comment(
    @BsonId
    val id: String = ObjectId().toHexString(),
    val addedAt: Long,
    val ownerId: String,
    val content: String,
    val contentId: String,
    val edited: Boolean = false,
) {
    fun toCommentResponse() = CommentResponse(
        id, addedAt, ownerId, content, contentId
    )
}
