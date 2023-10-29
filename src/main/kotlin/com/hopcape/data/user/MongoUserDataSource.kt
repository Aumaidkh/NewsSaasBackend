package com.hopcape.data.user

import com.hopcape.domain.user.UserDataSource
import com.hopcape.utils.Constants.USERS_TABLE_NAME
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

/**
 * Implementation of the [UserDataSource] which uses
 * MongoDB*/
class MongoUserDataSource(
    private val db: MongoDatabase
): UserDataSource {

    private val usersCollection by lazy {
        db.getCollection<User>(USERS_TABLE_NAME)
    }

    override suspend fun insertUser(user: User): Boolean {
        return safeDatabaseOperation {
            usersCollection.insertOne(
                document = user
            ).wasAcknowledged()
        } ?: false
    }

    override suspend fun getUserByEmail(email: String): User? {
        return safeDatabaseOperation {
            usersCollection.find(
                eq(User::email.name,email)
            ).firstOrNull()
        }
    }

    override suspend fun getAllUsers(): List<User> {
        return safeDatabaseOperation {
            usersCollection.find().toList()
        } ?: emptyList()
    }

    override suspend fun deleteUserByUserId(userId: String): Boolean {
        return safeDatabaseOperation {
            usersCollection.deleteOne(
                eq("_id",userId)
            ).wasAcknowledged()
        } ?: false
    }

    override suspend fun deleteUserByEmail(email: String): Boolean {
        return safeDatabaseOperation {
            usersCollection.deleteOne(
                eq(User::email.name,email)
            ).wasAcknowledged()
        } ?: false
    }

    override suspend fun updateUser(user: User): Boolean {
        return safeDatabaseOperation {
            usersCollection.updateOne(
                eq(User::email.name,user.email),
                listOf(
                    Updates.set(User::fullName.name,user.fullName),
                    Updates.set(User::profilePic.name,user.profilePic),
                    Updates.set(User::phoneNumber.name,user.phoneNumber),
                )
            ).wasAcknowledged()
        } ?: false
    }

    override suspend fun getUserById(userId: String): User? {
        return safeDatabaseOperation {
            usersCollection.find(
                eq("_id",userId)
            ).firstOrNull()
        }
    }
}