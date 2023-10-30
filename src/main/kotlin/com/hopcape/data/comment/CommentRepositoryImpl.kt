package com.hopcape.data.comment

import com.hopcape.data.user.safeDatabaseOperation
import com.hopcape.domain.comment.CommentDatasource
import com.hopcape.domain.comment.CommentRepository
import com.hopcape.utils.Constants
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.*

class CommentRepositoryImpl(
    private val datasource: CommentDatasource
): CommentRepository {

    override suspend fun insertComment(comment: Comment) =
        datasource.insertComment(comment)

    override suspend fun updateComment(comment: Comment) =
        datasource.updateComment(comment)

    override suspend fun deleteComment(comment: Comment) =
        datasource.deleteComment(comment)

    override suspend fun getCommentsForContent(contentId: String) =
        datasource.getCommentsForContent(contentId)

    override suspend fun getCommentsForContent(contentId: String, pageNumber: Int, limit: Int) =
        datasource.getCommentsForContent(contentId,pageNumber,limit)

    override suspend fun getCommentsByUser(userId: String) =
        datasource.getCommentsByUser(userId)

    override suspend fun getCommentsByUser(userId: String, pageNumber: Int, limit: Int) =
        datasource.getCommentsByUser(userId,pageNumber,limit)

    override suspend fun getCommentById(commentId: String) =
        datasource.getCommentById(commentId)

    override suspend fun deleteCommentsForContent(contentId: String) =
        datasource.deleteCommentsForContent(contentId)

    override suspend fun deleteCommentsByUser(userId: String) =
        datasource.deleteCommentsByUser(userId)

    override suspend fun getCommentCountForContent(contentId: String) =
        datasource.getCommentCountForContent(contentId)

    override suspend fun getCommentsByTimestamp(timestamp: Long, limit: Int) =
        datasource.getCommentsByTimestamp(timestamp,limit)

    override suspend fun getRecentComments(contentId: String, limit: Int): List<Comment> =
        datasource.getRecentComments(contentId,limit)

    override suspend fun getCommentsFlowFor(contentId: String) =
        datasource.getCommentsFlowFor(contentId)
}