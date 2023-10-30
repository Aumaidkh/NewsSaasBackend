package com.hopcape.data.comment

import com.hopcape.data.user.safeDatabaseOperation
import com.hopcape.domain.comment.CommentDatasource
import com.hopcape.utils.Constants
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.*

class MongoCommentDatasource(
    private val db: MongoDatabase
): CommentDatasource {

    private val commentCollection by lazy {
        db.getCollection<Comment>(Constants.COMMENTS_TABLE_NAME)
    }
    override suspend fun insertComment(comment: Comment): Boolean {
        return safeDatabaseOperation {
            commentCollection
                .insertOne(comment)
                .wasAcknowledged()
        } ?: false
    }

    override suspend fun updateComment(comment: Comment): Boolean {
        return safeDatabaseOperation {
            commentCollection
                .updateOne(
                    Filters.eq("_id",comment.id),
                    Updates.combine(
                        Updates.set(Comment::content.name,comment.content),
                        Updates.set(Comment::edited.name,true),
                    )
                ).modifiedCount > 0
        } ?: false
    }

    override suspend fun deleteComment(comment: Comment): Boolean {
        return safeDatabaseOperation {
            commentCollection
                .deleteOne(
                    Filters.eq("_id",comment.id)
                ).deletedCount > 0
        } ?: false
    }

    override suspend fun getCommentsForContent(contentId: String): List<Comment> {
        return safeDatabaseOperation {
            commentCollection
                .find(Filters.eq(Comment::contentId.name,contentId))
                .sort(Sorts.descending(Comment::addedAt.name))
                .toList()
        } ?: emptyList()
    }

    override suspend fun getCommentsForContent(contentId: String, pageNumber: Int, limit: Int): List<Comment> {
        return safeDatabaseOperation {
            commentCollection
                .find(Filters.eq(Comment::contentId.name,contentId))
                .skip(pageNumber)
                .sort(Sorts.descending(Comment::addedAt.name))
                .limit(limit)
                .toList()
        } ?: emptyList()
    }

    override suspend fun getCommentsByUser(userId: String): List<Comment> {
        return safeDatabaseOperation {
            commentCollection
                .find(Filters.eq(Comment::ownerId.name,userId))
                .sort(Sorts.descending(Comment::addedAt.name))
                .toList()
        } ?: emptyList()
    }

    override suspend fun getCommentsByUser(userId: String, pageNumber: Int, limit: Int): List<Comment> {
        return safeDatabaseOperation {
            commentCollection
                .find(Filters.eq(Comment::ownerId.name,userId))
                .sort(Sorts.descending(Comment::addedAt.name))
                .skip(pageNumber)
                .limit(limit)
                .toList()
        } ?: emptyList()
    }

    override suspend fun getCommentById(commentId: String): Comment? {
        return safeDatabaseOperation {
            commentCollection
                .find(Filters.eq("_id",commentId))
                .firstOrNull()
        }
    }

    override suspend fun deleteCommentsForContent(contentId: String): Boolean {
        return safeDatabaseOperation {
            commentCollection
                .deleteMany(
                    Filters.eq(Comment::contentId.name,contentId)
                )
                .deletedCount > 0
        } ?: false
    }

    override suspend fun deleteCommentsByUser(userId: String): Boolean {
        return safeDatabaseOperation {
            commentCollection
                .deleteMany(
                    Filters.eq(Comment::ownerId.name,userId)
                )
                .deletedCount > 0
        } ?: false
    }

    override suspend fun getCommentCountForContent(contentId: String): Long {
        return safeDatabaseOperation {
            commentCollection
                .countDocuments(
                    Filters.eq(Comment::contentId.name,contentId)
                )
        } ?: 0
    }

    override suspend fun getCommentsByTimestamp(timestamp: Long, limit: Int): List<Comment> {
        return safeDatabaseOperation {
            commentCollection
                .find(Filters.eq(Comment::addedAt.name,timestamp))
                .limit(limit)
                .sort(Sorts.descending(Comment::addedAt.name))
                .toList()
        } ?: emptyList()
    }

    override suspend fun getRecentComments(contentId: String, limit: Int): List<Comment> {
        return safeDatabaseOperation {
            commentCollection
                .find(Filters.eq(Comment::contentId.name,contentId))
                .sort(Sorts.descending(Comment::addedAt.name))
                .limit(limit)
                .toList()
        } ?: emptyList()
    }

    override suspend fun getCommentsFlowFor(contentId: String): Flow<List<Comment>> {

        return safeDatabaseOperation {
            commentCollection
                .find(Filters.eq(Comment::contentId.name,contentId))
                .toList()
                .toList()
                .run {
                    flowOf(this)
                }
        } ?: emptyFlow()
    }
}