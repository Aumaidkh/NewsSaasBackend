package com.hopcape.data.user

import com.hopcape.domain.repository.UserRepository
import com.hopcape.domain.security.hashing.HashingService
import com.hopcape.domain.security.hashing.SaltedHash
import com.hopcape.domain.security.token.TokenClaim
import com.hopcape.domain.security.token.TokenConfig
import com.hopcape.domain.security.token.TokenService
import com.hopcape.domain.user.UserDataSource

/**
 * Contains all the business logic associated with the
 * [User]*/
class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val tokenService: TokenService,
    private val hashingService: HashingService
): UserRepository {

    override suspend fun getUsers(): List<User> {
        return userDataSource.getAllUsers()
    }

    override suspend fun createUser(user: User): Boolean {
        // Create Hash for the password of the user
        val saltedHash =
            hashingService.generateSaltedHash(
                value = user.password
            )

        // Save the user using datasource
        return userDataSource.insertUser(
            user = user.copy(
                password = saltedHash.hash,
                salt = saltedHash.salt
            )
        )
    }

    override suspend fun authenticateUser(email: String, password: String, config: TokenConfig): String {
        // Get the user associated with email
        val user =
            userDataSource.getUserByEmail(
                email = email
            ) ?: throw UserRepository.IncorrectUsername()

        val passwordVerified =
            hashingService.verify(
                value = password,
                saltedHash = SaltedHash(
                    hash = user.password,
                    salt = user.salt
                )
            )
        if (!passwordVerified) {
            throw UserRepository.IncorrectPassword()
        }


        // The token will be having userId and email in its payload
        return tokenService.generate(
            config = config,
            claim = arrayOf(
                TokenClaim("userId", user.id.toString()),
                TokenClaim("email", user.email),
            )
        )

    }

    override suspend fun deleteUser(by: UserRepository.Operation.DeleteBy): Boolean {
        return when(by){
            is UserRepository.Operation.DeleteBy.Email -> {
                userDataSource.deleteUserByEmail(email = by.value)
            }
            is UserRepository.Operation.DeleteBy.Id -> {
                userDataSource.deleteUserByUserId(userId = by.value)
            }
        }
    }

    override suspend fun updateUser(user: User): Boolean {
        return userDataSource.updateUser(user)
    }

    override suspend fun getUserById(userId: String): User? {
        return userDataSource.getUserById(userId)
    }
}