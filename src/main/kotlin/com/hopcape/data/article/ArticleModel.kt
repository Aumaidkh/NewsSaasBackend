package com.hopcape.data.article

import com.hopcape.data.response.article.ArticleResponse
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class ArticleModel(
    @BsonId
    val id: ObjectId = ObjectId(),
    val title: String,
    val subtitle: String,
    val thumbnail: String,
    val sponsored: Boolean = false,
    val category: String,
    val date: Double,
    val content: String
) {
    fun toArticleResponse() = ArticleResponse(
        id = id.toHexString(),
        title = title,
        subtitle = subtitle,
        thumbnail = thumbnail,
        sponsored = sponsored,
        category = category,
        date = date.toLong(),
        content = content
    )
}
