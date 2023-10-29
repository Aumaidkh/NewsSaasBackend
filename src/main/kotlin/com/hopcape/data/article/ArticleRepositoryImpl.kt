package com.hopcape.data.article

import com.hopcape.domain.article.ArticleDataSource
import com.hopcape.domain.repository.ArticleRepository
/**
 * Implementation of the [ArticleRepository] interface that serves as an intermediary
 * between the data source and the application, providing methods to manage articles.
 *
 * @param articleDataSource The data source responsible for handling article data.
 */
class ArticleRepositoryImpl(
    private val articleDataSource: ArticleDataSource
) : ArticleRepository {

    /**
     * Inserts an article into the repository.
     *
     * @param articleModel The article model to be inserted.
     * @return `true` if the insertion is successful, `false` otherwise.
     */
    override suspend fun insertArticle(articleModel: ArticleModel): Boolean {
        return articleDataSource.insertArticle(articleModel)
    }

    /**
     * Deletes an article from the repository.
     *
     * @param articleModel The article model to be deleted.
     * @return `true` if the deletion is successful, `false` otherwise.
     */
    override suspend fun deleteArticle(articleModel: ArticleModel): Boolean {
        return articleDataSource.deleteArticle(articleModel)
    }

    /**
     * Retrieves a list of all articles from the repository.
     *
     * @return A list of all articles in the repository.
     */
    override suspend fun getAllArticles(): List<ArticleModel> {
        return articleDataSource.getAllArticles()
    }

    /**
     * Retrieves a list of articles with pagination support from the repository.
     *
     * @param offset The starting index for pagination.
     * @param limit The maximum number of articles to retrieve (default is 10).
     * @return A list of articles within the specified range.
     */
    override suspend fun getPagedArticles(offset: Int, limit: Int): List<ArticleModel> {
        return articleDataSource.getPagedArticles(offset, limit)
    }

    /**
     * Updates an existing article in the repository.
     *
     * @param articleModel The article model to be updated.
     * @return `true` if the update is successful, `false` otherwise.
     */
    override suspend fun updateArticle(articleModel: ArticleModel): Boolean {
        return articleDataSource.updateArticle(articleModel)
    }

    /**
     * Retrieves an article from the repository based on a search query.
     *
     * @param query The search query to find the article (case-insensitive).
     * @return The article that matches the query, or `null` if no matching article is found.
     */
    override suspend fun getArticleByQuery(query: String): ArticleModel? {
        return articleDataSource.getArticleByQuery(query)
    }

    /**
     * Retrieves an article from the repository based on its unique identifier.
     *
     * @param id The unique identifier of the article to retrieve.
     * @return The article with the specified `id`, or `null` if no matching article is found.
     */
    override suspend fun getArticleById(id: String): ArticleModel? {
        return articleDataSource.getArticleById(id)
    }
}
