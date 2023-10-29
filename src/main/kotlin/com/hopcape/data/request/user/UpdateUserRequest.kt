package com.hopcape.data.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val id: String,
    val fullName: String? = null,
    val phoneNumber: String? = null,
    val profilePic: String? = null,
)
