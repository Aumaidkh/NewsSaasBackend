package com.hopcape.data.comment

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
)
