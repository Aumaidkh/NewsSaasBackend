package com.hopcape.domain.repository

import com.hopcape.data.article.ArticleModel

/**
 * An interface representing a repository for managing articles.
 */
interface ArticleRepository {

    /**
     * Inserts an article into the data source.
     *
     * @param articleModel The article model to be inserted.
     * @return `true` if the insertion is successful, `false` otherwise.
     */
    suspend fun insertArticle(articleModel: ArticleModel): Boolean

    /**
     * Deletes an article from the data source.
     *
     * @param articleModel The article model to be deleted.
     * @return `true` if the deletion is successful, `false` otherwise.
     */
    suspend fun deleteArticle(articleModel: ArticleModel): Boolean

    /**
     * Retrieves a list of all articles stored in the data source.
     *
     * @return A list of all articles in the data source.
     */
    suspend fun getAllArticles(): List<ArticleModel>

    /**
     * Retrieves a list of articles with pagination support.
     *
     * @param offset The starting index for pagination.
     * @param limit The maximum number of articles to retrieve (default is 10).
     * @return A list of articles within the specified range.
     */
    suspend fun getPagedArticles(offset: Int, limit: Int = 10): List<ArticleModel>

    /**
     * Updates an existing article in the data source.
     *
     * @param articleModel The article model to be updated.
     * @return `true` if the update is successful, `false` otherwise.
     */
    suspend fun updateArticle(articleModel: ArticleModel): Boolean

    /**
     * Retrieves an article based on a query.
     *
     * @param query The search query to find the article.
     * @return The article that matches the query, or `null` if no matching article is found.
     */
    suspend fun getArticleByQuery(query: String): ArticleModel?


    /**
     * Retrieves an article by its unique identifier.
     *
     * This function queries the data source to retrieve an article based on its unique identifier,
     * represented by the provided `id`. If an article with the specified `id` exists in the data source,
     * it will be returned. If no matching article is found, this function returns `null`.
     *
     * @param id The unique identifier of the article to retrieve.
     * @return The article with the specified `id`, or `null` if no matching article is found.
     */
    suspend fun getArticleById(id: String): ArticleModel?

}