package com.hopcape.domain.repository

import com.hopcape.data.user.User
import com.hopcape.domain.security.token.TokenConfig

interface UserRepository {

    /**
     * @return list of [User]
     * saved in datasource*/
    suspend fun getUsers(): List<User>

    /**
     * Creates a user
     * @param user into the database
     * @return [Boolean]*/
    suspend fun createUser(user: User): Boolean

    /**
     * Authenticates a user by
     * @param email
     * @param password
     * @param config - configuration used while creating token
     * @throws IncorrectPassword
     * @throws IncorrectUsername
     * @return token [String]*/
    suspend fun authenticateUser(email: String,password: String,config: TokenConfig): String

    /**
     * Delete a user with */
    suspend fun deleteUser(by: Operation.DeleteBy): Boolean

    /**
     * Updates a
     * @param user
     * @return [Boolean]*/
    suspend fun updateUser(user: User): Boolean

    /**
     * Get User by
     * @param userId
     * @return [User]*/
    suspend fun getUserById(userId: String): User?

    class IncorrectPassword: Exception("Incorrect Password")

    class IncorrectUsername: Exception("Incorrect Username or Password")

    sealed interface Operation{
        sealed interface DeleteBy{
            data class Email(val value: String): DeleteBy
            data class Id(val value: String): DeleteBy
        }
    }
}

