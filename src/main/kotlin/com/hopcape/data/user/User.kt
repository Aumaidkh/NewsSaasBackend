package com.hopcape.data.user

import com.hopcape.data.response.SerializableUser
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


/**
 * An item inside the User table of the database
 * @param id - Unique identifier
 * @param email
 * @param password - Hashed Password
 * @param salt - Salt that was used while hashing the password
 * */
data class User(
    @BsonId val id: String = ObjectId().toHexString(),
    val email: String,
    val password: String,
    val salt: String = "",
    val fullName: String = "",
    val phoneNumber: String? = null,
    val profilePic: String? = null
) {

    fun toSerializableUser(): SerializableUser {
        return SerializableUser(
            id = id,
            email = email,
            password = password,
            salt = salt,
            fullName = fullName,
            phoneNumber = phoneNumber,
            profilePic = profilePic
        )
    }
}
