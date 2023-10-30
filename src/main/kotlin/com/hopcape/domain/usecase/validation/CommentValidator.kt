package com.hopcape.domain.usecase.validation

import com.hopcape.data.request.comment.CommentRequest
import com.hopcape.domain.repository.ArticleRepository

/**
 * Validates the [CommentRequest]
 * performs the following checks
 * 1. Content of the comment should not be empty
 * 2. User id should not be blank
 * 3. Article exists */
class CommentValidator(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(request: CommentRequest): ValidationResult {
        if (request.content.isBlank()){
            return ValidationResult(
                valid = false,
                message = "Please ensure the comment is not empty"
            )
        }

        if (request.userId.isBlank()){
            return ValidationResult(
                valid = false,
                message = "Please ensure the user id is not empty"
            )
        }

        if (!articleExists(request.contentId)){
            return ValidationResult(
                valid = false,
                message = "The article doesn't exist"
            )
        }

        return ValidationResult(
            valid = true
        )
    }

    private suspend fun articleExists(id: String) =
        articleRepository.getArticleById(id) != null
}

