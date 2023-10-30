package com.hopcape.domain.comment

import com.hopcape.data.comment.Comment
import kotlinx.coroutines.flow.Flow


/**
 * Interface for managing comments in a data source.
 */
interface CommentDatasource {

    /**
     * Inserts a comment associated with a specific content item.
     *
     * @param comment The comment to be inserted.
     * @return `true` if the insertion is successful, `false` otherwise.
     */
    suspend fun insertComment(comment: Comment): Boolean

    /**
     * Updates an existing comment.
     *
     * @param comment The comment to be updated.
     * @return `true` if the update is successful, `false` otherwise.
     */
    suspend fun updateComment(comment: Comment): Boolean

    /**
     * Deletes a comment.
     *
     * @param comment The comment to be deleted.
     * @return `true` if the deletion is successful, `false` otherwise.
     */
    suspend fun deleteComment(comment: Comment): Boolean

    /**
     * Retrieves a list of comments associated with a specific content item.
     *
     * @param contentId The unique identifier of the associated content.
     * @return A list of comments related to the specified content.
     */
    suspend fun getCommentsForContent(contentId: String): List<Comment>

    /**
     * Retrieves a list of comments associated with a specific content item.
     * in pages
     * @param pageNumber
     * @param limit
     * @param contentId The unique identifier of the associated content.
     * @return A list of comments related to the specified content.
     */
    suspend fun getCommentsForContent(contentId: String,pageNumber: Int, limit: Int = 10): List<Comment>

    /**
     * Retrieves a list of comments posted by a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return A list of comments posted by the specified user.
     */
    suspend fun getCommentsByUser(userId: String): List<Comment>

    /**
     * Retrieves a list of comments posted by a specific user.
     * with pagination
     * @param userId The unique identifier of the user.
     * @param pageNumber
     * @param limit
     * @return A list of comments posted by the specified user.
     */
    suspend fun getCommentsByUser(userId: String,pageNumber: Int, limit: Int): List<Comment>

    /**
     * Retrieves a specific comment based on its unique identifier.
     *
     * @param commentId The unique identifier of the comment.
     * @return The comment with the specified ID, or `null` if it doesn't exist.
     */
    suspend fun getCommentById(commentId: String): Comment?

    /**
     * Deletes all comments associated with a specific content item.
     *
     * @param contentId The unique identifier of the associated content.
     * @return `true` if the deletion is successful, `false` otherwise.
     */
    suspend fun deleteCommentsForContent(contentId: String): Boolean

    /**
     * Deletes all comments posted by a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return `true` if the deletion is successful, `false` otherwise.
     */
    suspend fun deleteCommentsByUser(userId: String): Boolean

    /**
     * Returns the total count of comments associated with a specific content item.
     *
     * @param contentId The unique identifier of the associated content.
     * @return The count of comments related to the specified content.
     */
    suspend fun getCommentCountForContent(contentId: String): Long

    /**
     * Retrieves comments posted after a specified timestamp, optionally limiting the number of results.
     *
     * @param timestamp The timestamp as a reference point for selecting comments.
     * @param limit The maximum number of comments to retrieve (optional).
     * @return A list of comments posted after the specified timestamp.
     */
    suspend fun getCommentsByTimestamp(timestamp: Long, limit: Int): List<Comment>

    /**
     * Retrieves the most recent comments, optionally limiting the number of results.
     *
     * @param limit The maximum number of comments to retrieve (optional).
     * @return A list of the most recent comments.
     */
    suspend fun getRecentComments(limit: Int): List<Comment>


    /**
     * Returns a flow of [Comment]
     * for a specific
     * @param contentId*/
    suspend fun getCommentsFlowFor(contentId: String): Flow<List<Comment>>
}
