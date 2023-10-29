package com.hopcape.data.article

import com.hopcape.data.user.safeDatabaseOperation
import com.hopcape.domain.article.ArticleDataSource
import com.hopcape.utils.Constants.ARTICLES_TABLE_NAME
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
/**
 * Implementation of the [ArticleDataSource] interface that uses a MongoDB collection to manage articles.
 *
 * @param db The MongoDB database where article data is stored.
 */
class MongoArticleDatasource(
    private val db: MongoDatabase
) : ArticleDataSource {

    // Lazily initialize the MongoDB collection for articles
    private val articleCollection by lazy {
        db.getCollection<ArticleModel>(ARTICLES_TABLE_NAME)
    }

    /**
     * Inserts an article into the MongoDB collection.
     *
     * @param articleModel The article model to be inserted.
     * @return `true` if the insertion is successful, `false` otherwise.
     */
    override suspend fun insertArticle(articleModel: ArticleModel): Boolean {
        return safeDatabaseOperation {
            articleCollection
                .insertOne(articleModel)
                .wasAcknowledged()
        } ?: false
    }

    /**
     * Deletes an article from the MongoDB collection based on its unique identifier.
     *
     * @param articleModel The article model to be deleted.
     * @return `true` if the deletion is successful, `false` otherwise.
     */
    override suspend fun deleteArticle(articleModel: ArticleModel): Boolean {
        return safeDatabaseOperation {
            articleCollection
                .deleteOne(
                    Filters.eq("_id", articleModel.id)
                )
                .wasAcknowledged()
        } ?: false
    }

    /**
     * Retrieves a list of all articles stored in the MongoDB collection.
     *
     * @return A list of all articles in the collection.
     */
    override suspend fun getAllArticles(): List<ArticleModel> {
        return safeDatabaseOperation {
            articleCollection
                .find()
                .toList()
        } ?: emptyList()
    }

    /**
     * Retrieves a list of articles with pagination support from the MongoDB collection.
     *
     * @param offset The starting index for pagination.
     * @param limit The maximum number of articles to retrieve (default is 10).
     * @return A list of articles within the specified range.
     */
    override suspend fun getPagedArticles(offset: Int, limit: Int): List<ArticleModel> {
        return safeDatabaseOperation {
            articleCollection
                .find()
                .skip(offset)
                .limit(limit)
                .toList()
        } ?: emptyList()
    }

    /**
     * Updates an existing article in the MongoDB collection.
     *
     * @param articleModel The article model to be updated.
     * @return `true` if the update is successful, `false` otherwise.
     */
    override suspend fun updateArticle(articleModel: ArticleModel): Boolean {
        return safeDatabaseOperation {
            articleCollection
                .findOneAndUpdate(
                    Filters.eq("_id", articleModel.id),
                    Updates.combine(
                        Updates.set(ArticleModel::category.name, articleModel.category),
                        Updates.set(ArticleModel::title.name, articleModel.title),
                        Updates.set(ArticleModel::subtitle.name, articleModel.subtitle),
                        Updates.set(ArticleModel::thumbnail.name, articleModel.thumbnail),
                        Updates.set(ArticleModel::date.name, articleModel.date),
                        Updates.set(ArticleModel::content.name, articleModel.content)
                    )
                )
            true
        } == true
    }

    /**
     * Retrieves an article from the MongoDB collection based on a search query.
     *
     * @param query The search query to find the article (case-insensitive).
     * @return The article that matches the query, or `null` if no matching article is found.
     */
    override suspend fun getArticleByQuery(query: String): ArticleModel? {
        return safeDatabaseOperation {
            val regexQuery = query.toRegex(RegexOption.IGNORE_CASE)
            articleCollection
                .find(
                    Filters.regex(ArticleModel::title.name, regexQuery.pattern)
                )
                .firstOrNull()
        }
    }

    /**
     * Retrieves an article from the MongoDB collection based on its unique identifier.
     *
     * @param id The unique identifier of the article to retrieve.
     * @return The article with the specified `id`, or `null` if no matching article is found.
     */
    override suspend fun getArticleById(id: String): ArticleModel? {
        return safeDatabaseOperation {
            articleCollection
                .find(
                    Filters.eq("_id", id)
                )
                .firstOrNull()
        }
    }
}
