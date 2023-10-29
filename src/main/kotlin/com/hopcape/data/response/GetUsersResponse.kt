package com.hopcape.data.response

import com.hopcape.data.user.User
import kotlinx.serialization.Serializable

/**
 * Wrapper around the response which
 * is sent on a request when user is trying to get
 * list of users [User]*/
@Serializable
data class GetUsersResponse(
    val users: List<SerializableUser>
)
@Serializable
data class SerializableUser(
    val id: String,
    val email: String,
    val password: String,
    val salt: String = "",
    val fullName: String,
    val phoneNumber: String?,
    val profilePic: String?
)
