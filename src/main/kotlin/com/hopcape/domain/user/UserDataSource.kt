package com.hopcape.domain.user

import com.hopcape.data.user.User

/**
 * Exposes all the set of CRUD operations
 * associated with the [User]
 * */
interface UserDataSource {

    /**
     * Inserts a
     * @param user into the datasource
     * @return [Boolean] if user was saved or not
     * */
    suspend fun insertUser(user: User): Boolean

    /**
     * @return [User]
     * by
     * @param email
     * if user is not found returns null*/
    suspend fun getUserByEmail(email: String): User?

    /**
     * @return list of [User] */
    suspend fun getAllUsers(): List<User>

    /**
     * Deletes a user with
     * @param userId
     * @return [Boolean] whether user was deleted or not
     * */
    suspend fun deleteUserByUserId(userId: String):Boolean

    /**
     * Deletes a user with
     * @param email
     * @return [Boolean] whether user was deleted or not
     * */
    suspend fun deleteUserByEmail(email: String):Boolean


    /**
     * Updates a
     * @param user
     * @return [Boolean] whether user was updated or not*/
    suspend fun updatedUser(user: User): Boolean


    /**
     * @return [User]
     * by @param userId*/
    suspend fun getUserById(userId: String): User?
}